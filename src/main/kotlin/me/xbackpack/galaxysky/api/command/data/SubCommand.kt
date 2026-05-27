package me.xbackpack.galaxysky.api.command.data

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import me.xbackpack.galaxysky.api.command.common.ArgumentGetter
import me.xbackpack.galaxysky.api.command.common.CommandDsl
import me.xbackpack.galaxysky.api.command.common.Cooldown
import me.xbackpack.galaxysky.api.command.function.ConsoleCommandFunction
import me.xbackpack.galaxysky.api.command.function.PlayerCommandFunction
import me.xbackpack.galaxysky.api.command.function.WrappedSenderRequirement
import me.xbackpack.galaxysky.api.command.node.Node
import me.xbackpack.galaxysky.api.command.node.NodeBuilder
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

@CommandDsl
data class SubCommand(
    override val name: String,
    override val requirement: WrappedSenderRequirement,
) : Node {
    override var cooldown: Cooldown? = null

    override var playerFunction: ((Player, ArgumentGetter) -> PlayerCommandFunction)? = null
    override var consoleFunction: ((ConsoleCommandSender, ArgumentGetter) -> ConsoleCommandFunction)? = null

    override val subcommands: MutableSet<SubCommand> = mutableSetOf()
    override val optionals: MutableSet<OptionalArgument> = mutableSetOf()
    override val arguments: MutableSet<RequiredArgument> = mutableSetOf()

    fun build(): LiteralArgumentBuilder<CommandSourceStack> = NodeBuilder(Commands.literal(name), this).final
}
