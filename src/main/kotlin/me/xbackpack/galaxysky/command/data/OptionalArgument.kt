package me.xbackpack.galaxysky.command.data

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import me.xbackpack.galaxysky.command.common.ArgumentGetter
import me.xbackpack.galaxysky.command.common.CommandDsl
import me.xbackpack.galaxysky.command.common.Cooldown
import me.xbackpack.galaxysky.command.function.ConsoleCommandFunction
import me.xbackpack.galaxysky.command.function.PlayerCommandFunction
import me.xbackpack.galaxysky.command.node.Node
import me.xbackpack.galaxysky.command.node.NodeBuilder
import me.xbackpack.galaxysky.enum.command.SenderRequirement
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

@CommandDsl
data class OptionalArgument(
    override val name: String,
    val type: ArgumentType<*>,
) : Node {
    override var requirement: SenderRequirement? = null
    override var permission: String? = null
    override var cooldown: Cooldown? = null

    override var playerFunction: (Player, ArgumentGetter) -> PlayerCommandFunction? =
        { _, _ -> null }
    override var staffFunction: (Player, ArgumentGetter) -> PlayerCommandFunction? =
        { _, _ -> null }
    override var consoleFunction: (ConsoleCommandSender, ArgumentGetter) -> ConsoleCommandFunction? =
        { _, _ -> null }

    override val subcommands: MutableSet<SubCommand> = mutableSetOf()
    override val optionals: MutableSet<OptionalArgument> = mutableSetOf()
    override val arguments: MutableSet<RequiredArgument> = mutableSetOf()

    override fun equals(other: Any?): Boolean = other is OptionalArgument && other.name == name

    override fun hashCode() = name.hashCode()

    fun build(): RequiredArgumentBuilder<CommandSourceStack, *> = NodeBuilder(Commands.argument(name, type), this).final
}
