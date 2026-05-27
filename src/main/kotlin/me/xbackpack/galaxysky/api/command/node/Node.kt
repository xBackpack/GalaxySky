package me.xbackpack.galaxysky.api.command.node

import com.mojang.brigadier.arguments.ArgumentType
import me.xbackpack.galaxysky.api.command.common.ArgumentGetter
import me.xbackpack.galaxysky.api.command.common.CommandDsl
import me.xbackpack.galaxysky.api.command.common.Cooldown
import me.xbackpack.galaxysky.api.command.data.OptionalArgument
import me.xbackpack.galaxysky.api.command.data.RequiredArgument
import me.xbackpack.galaxysky.api.command.data.SubCommand
import me.xbackpack.galaxysky.api.command.function.ConsoleCommandFunction
import me.xbackpack.galaxysky.api.command.function.PlayerCommandFunction
import me.xbackpack.galaxysky.api.command.function.WrappedSenderRequirement
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

@CommandDsl
interface Node {
    val name: String
    val requirement: WrappedSenderRequirement
    val cooldown: Cooldown?

    var playerFunction: ((Player, ArgumentGetter) -> PlayerCommandFunction)?
    var consoleFunction: ((ConsoleCommandSender, ArgumentGetter) -> ConsoleCommandFunction)?

    val subcommands: MutableSet<SubCommand>
    val optionals: MutableSet<OptionalArgument>
    val arguments: MutableSet<RequiredArgument>

    fun doForPlayer(block: PlayerCommandFunction.(Player, ArgumentGetter) -> Unit) {
        playerFunction = { player, getter ->
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
        requirement: WrappedSenderRequirement = this@Node.requirement,
        factory: SubCommand.() -> Unit,
    ) = subcommands.add(
        SubCommand(name, requirement)
            .apply(factory)
            .apply {
                cooldown = cooldown ?: this@Node.cooldown
            },
    )

    fun optional(
        name: String,
        requirement: WrappedSenderRequirement = this@Node.requirement,
        type: ArgumentType<*>,
        factory: OptionalArgument.() -> Unit,
    ) = optionals.add(
        OptionalArgument(name, requirement, type)
            .apply(factory)
            .apply {
                cooldown = cooldown ?: this@Node.cooldown
            },
    )

    fun argument(
        name: String,
        type: ArgumentType<*>,
    ) = arguments.add(RequiredArgument(name, type))
}
