package me.xbackpack.galaxysky.command.data

import com.mojang.brigadier.builder.LiteralArgumentBuilder
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
class Command : Node {
    override lateinit var name: String
    override var requirement: SenderRequirement? = SenderRequirement.ANY
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

    lateinit var description: String
    var aliases = emptyList<String>()

    fun build(): LiteralArgumentBuilder<CommandSourceStack> = NodeBuilder(Commands.literal(name), this).final

    companion object {
        fun create(builder: Command.() -> Unit) = Command().apply(builder)
    }
}
