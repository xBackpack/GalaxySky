package me.xbackpack.galaxysky.command.node

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import io.papermc.paper.command.brigadier.CommandSourceStack
import me.xbackpack.galaxysky.command.common.CommandDsl
import me.xbackpack.galaxysky.command.common.Cooldown
import me.xbackpack.galaxysky.command.data.OptionalArgument
import me.xbackpack.galaxysky.command.data.RequiredArgument
import me.xbackpack.galaxysky.command.data.SubCommand
import me.xbackpack.galaxysky.command.function.ConsoleCommandFunction
import me.xbackpack.galaxysky.command.function.PlayerCommandFunction
import me.xbackpack.galaxysky.command.function.StaffCommandFunction
import me.xbackpack.galaxysky.enum.command.SenderRequirement
import me.xbackpack.galaxysky.service.LuckPermsService
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

@CommandDsl
interface Node {
    val name: String
    var requirement: SenderRequirement
    var permission: String?
    var cooldown: Cooldown?

    var playerFunction: PlayerCommandFunction?
    var staffFunction: StaffCommandFunction?
    var consoleFunction: ConsoleCommandFunction?

    val subcommands: MutableSet<SubCommand>
    val optionals: MutableSet<OptionalArgument>
    val arguments: MutableSet<RequiredArgument>

    fun doForPlayer(function: PlayerCommandFunction.() -> Unit) {
        playerFunction = PlayerCommandFunction().apply(function)
    }

    fun doForStaff(function: StaffCommandFunction.() -> Unit) {
        staffFunction = StaffCommandFunction().apply(function)
    }

    fun doForConsole(function: ConsoleCommandFunction.() -> Unit) {
        consoleFunction = ConsoleCommandFunction().apply(function)
    }

    fun subcommand(
        name: String,
        factory: SubCommand.() -> Unit,
    ) = subcommands.add(SubCommand(name).apply(factory))

    fun optional(
        name: String,
        type: ArgumentType<*>,
        factory: OptionalArgument.() -> Unit,
    ) = optionals.add(OptionalArgument(name, type).apply(factory))

    fun argument(
        name: String,
        type: ArgumentType<*>,
    ) = arguments.add(RequiredArgument(name, type))

    fun <T : ArgumentBuilder<CommandSourceStack, T>> internalBuild(arg: T) =
        NodeBuilder(arg, this) { sender, arguments ->
            when (requirement) {
                SenderRequirement.PLAYER -> {
                    val player = sender as Player
                    playerFunction?.runAll(player, arguments)
                }
                SenderRequirement.STAFF -> {
                    val player = sender as Player
                    staffFunction?.runAll(player, arguments)
                }
                SenderRequirement.STAFF_OR_CONSOLE -> {
                    (sender as? Player)?.let { player ->
                        staffFunction?.runAll(player, arguments)
                    }

                    (sender as? ConsoleCommandSender)?.let { console ->
                        consoleFunction?.runAll(console, arguments)
                    }
                }
                SenderRequirement.ANY -> {
                    (sender as? Player)?.let { player ->
                        if (LuckPermsService.isStaff(player)) {
                            staffFunction?.runAll(player, arguments)
                        } else {
                            playerFunction?.runAll(player, arguments)
                        }
                    }

                    (sender as? ConsoleCommandSender)?.let { console ->
                        consoleFunction?.runAll(console, arguments)
                    }
                }
            }
        }.final
}
