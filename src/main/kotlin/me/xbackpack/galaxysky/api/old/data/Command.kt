package me.xbackpack.galaxysky.api.old.data

import com.mojang.brigadier.builder.LiteralArgumentBuilder
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
import me.xbackpack.galaxysky.service.ListenerService
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import org.bukkit.event.Event

@CommandDsl
class Command(
    override val name: String,
    val description: String,
    override val requirement: WrappedSenderRequirement,
) : Node {
    override var cooldown: Cooldown? = null
    var aliases: List<String> = emptyList()

    override var playerFunction: ((Player, ArgumentGetter) -> PlayerCommandFunction)? = null
    override var consoleFunction: ((ConsoleCommandSender, ArgumentGetter) -> ConsoleCommandFunction)? = null

    override val subcommands: MutableSet<SubCommand> = mutableSetOf()
    override val optionals: MutableSet<OptionalArgument> = mutableSetOf()
    override val arguments: MutableSet<RequiredArgument> = mutableSetOf()

    inline fun <reified T : Event> listener(crossinline handler: (T) -> Unit) = ListenerService.hookEvent<T>(handler)

    fun build(): LiteralArgumentBuilder<CommandSourceStack> = NodeBuilder(Commands.literal(name), this).final

    companion object {
        fun create(
            name: String,
            description: String,
            requirement: WrappedSenderRequirement,
            builder: Command.() -> Unit,
        ) = Command(name, description, requirement).apply(builder)
    }
}
