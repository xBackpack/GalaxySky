package me.xbackpack.galaxysky.api.old.data

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import me.xbackpack.galaxysky.api.old.common.ArgumentGetter
import me.xbackpack.galaxysky.api.old.common.CommandDsl
import me.xbackpack.galaxysky.api.old.common.Cooldown
import me.xbackpack.galaxysky.api.old.function.ConsoleCommandFunction
import me.xbackpack.galaxysky.api.old.function.PlayerCommandFunction
import me.xbackpack.galaxysky.api.old.function.WrappedSenderRequirement
import me.xbackpack.galaxysky.api.old.node.Node
import me.xbackpack.galaxysky.api.old.node.NodeBuilder
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

@CommandDsl
data class OptionalArgument(
    override val name: String,
    override val requirement: WrappedSenderRequirement,
    val type: ArgumentType<*>,
) : Node {
    override var cooldown: Cooldown? = null

    override var playerFunction: ((Player, ArgumentGetter) -> PlayerCommandFunction)? = null
    override var consoleFunction: ((ConsoleCommandSender, ArgumentGetter) -> ConsoleCommandFunction)? = null

    override val subcommands: MutableSet<SubCommand> = mutableSetOf()
    override val optionals: MutableSet<OptionalArgument> = mutableSetOf()
    override val arguments: MutableSet<RequiredArgument> = mutableSetOf()

    override fun equals(other: Any?): Boolean = other is OptionalArgument && other.name == name

    override fun hashCode() = name.hashCode()

    fun build(): RequiredArgumentBuilder<CommandSourceStack, *> = NodeBuilder(Commands.argument(name, type), this).final
}
