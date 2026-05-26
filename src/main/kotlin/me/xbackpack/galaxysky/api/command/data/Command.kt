package me.xbackpack.galaxysky.api.command.data

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import me.xbackpack.galaxysky.api.command.common.ArgumentGetter
import me.xbackpack.galaxysky.api.command.common.CommandDsl
import me.xbackpack.galaxysky.api.command.common.Cooldown
import me.xbackpack.galaxysky.api.command.function.ConsoleCommandFunction
import me.xbackpack.galaxysky.api.command.function.PlayerCommandFunction
import me.xbackpack.galaxysky.api.command.node.Node
import me.xbackpack.galaxysky.api.command.node.NodeBuilder
import me.xbackpack.galaxysky.enum.command.SenderRequirement
import me.xbackpack.galaxysky.service.ListenerService
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import org.bukkit.event.Event

@CommandDsl
class Command : Node {
    override lateinit var name: String
    override var requirement: SenderRequirement? = SenderRequirement.ANY
    override var permission: String? = null
    override var cooldown: Cooldown? = null

    override var playerFunction: ((Player, ArgumentGetter) -> PlayerCommandFunction)? = null
    override var consoleFunction: ((ConsoleCommandSender, ArgumentGetter) -> ConsoleCommandFunction)? = null

    override val subcommands: MutableSet<SubCommand> = mutableSetOf()
    override val optionals: MutableSet<OptionalArgument> = mutableSetOf()
    override val arguments: MutableSet<RequiredArgument> = mutableSetOf()

    lateinit var description: String
    var aliases = emptyList<String>()

    inline fun <reified T : Event> listener(crossinline handler: (T) -> Unit) = ListenerService.hookEvent<T>(handler)

    fun build(): LiteralArgumentBuilder<CommandSourceStack> = NodeBuilder(Commands.literal(name), this).final

    companion object {
        fun create(builder: Command.() -> Unit) = Command().apply(builder)
    }
}
