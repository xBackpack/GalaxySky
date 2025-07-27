package me.xbackpack.galaxysky.command.node

import com.mojang.brigadier.arguments.ArgumentType
import me.xbackpack.galaxysky.command.common.ArgumentGetter
import me.xbackpack.galaxysky.command.common.CommandDsl
import me.xbackpack.galaxysky.command.common.Cooldown
import me.xbackpack.galaxysky.command.data.OptionalArgument
import me.xbackpack.galaxysky.command.data.RequiredArgument
import me.xbackpack.galaxysky.command.data.SubCommand
import me.xbackpack.galaxysky.command.function.ConsoleCommandFunction
import me.xbackpack.galaxysky.command.function.PlayerCommandFunction
import me.xbackpack.galaxysky.enum.command.SenderRequirement
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

@CommandDsl
interface Node {
    val name: String
    var requirement: SenderRequirement?
    var permission: String?
    var cooldown: Cooldown?

    var playerFunction: (Player, ArgumentGetter) -> PlayerCommandFunction?
    var staffFunction: (Player, ArgumentGetter) -> PlayerCommandFunction?
    var consoleFunction: (ConsoleCommandSender, ArgumentGetter) -> ConsoleCommandFunction?

    val subcommands: MutableSet<SubCommand>
    val optionals: MutableSet<OptionalArgument>
    val arguments: MutableSet<RequiredArgument>

    fun doForPlayer(block: PlayerCommandFunction.(Player, ArgumentGetter) -> Unit) {
        playerFunction = { player, getter ->
            PlayerCommandFunction(player, getter).apply { block(player, getter) }
        }
    }

    fun doForStaff(block: PlayerCommandFunction.(Player, ArgumentGetter) -> Unit) {
        staffFunction = { player, getter ->
            PlayerCommandFunction(player, getter).apply { block(player, getter) }
        }
    }

    fun doForConsole(block: ConsoleCommandFunction.(ConsoleCommandSender, ArgumentGetter) -> Unit) {
        consoleFunction = { console, getter ->
            ConsoleCommandFunction(console, getter).apply { block(console, getter) }
        }
    }

    fun subcommand(
        name: String,
        factory: SubCommand.() -> Unit,
    ) = subcommands.add(
        SubCommand(name)
            .apply(factory)
            .apply {
                requirement = requirement ?: this@Node.requirement
                permission = permission ?: this@Node.permission
                cooldown = cooldown ?: this@Node.cooldown
            },
    )

    fun optional(
        name: String,
        type: ArgumentType<*>,
        factory: OptionalArgument.() -> Unit,
    ) = optionals.add(
        OptionalArgument(name, type)
            .apply(factory)
            .apply {
                requirement = requirement ?: this@Node.requirement
                permission = permission ?: this@Node.permission
                cooldown = cooldown ?: this@Node.cooldown
            },
    )

    fun argument(
        name: String,
        type: ArgumentType<*>,
    ) = arguments.add(RequiredArgument(name, type))
}
