package me.xbackpack.galaxysky.api.command.branches

import me.xbackpack.galaxysky.api.command.CommandDsl
import me.xbackpack.galaxysky.api.command.Cooldown
import me.xbackpack.galaxysky.api.command.Permission
import me.xbackpack.galaxysky.api.command.Requirement
import me.xbackpack.galaxysky.api.command.types.Branch
import me.xbackpack.galaxysky.api.util.ConsoleFunction
import me.xbackpack.galaxysky.api.util.PlayerFunction
import me.xbackpack.galaxysky.service.ListenerService.hookEvent
import org.bukkit.event.Event

@CommandDsl
class Command(
    override val name: String,
    val description: String,
    override val requirement: Requirement,
    override val permission: Permission?,
    val aliases: List<String>,
    override val cooldown: Cooldown?,
) : Branch {
    override val parent = null

    override val arguments = mutableListOf<RequiredArgument>()
    override var optional: OptionalArgument? = null
    override val subcommands = mutableListOf<Subcommand>()

    override val playerFunctions = mutableListOf<PlayerFunction>()
    override val consoleFunctions = mutableListOf<ConsoleFunction>()

    inline fun <reified T : Event> listener(crossinline handler: (T) -> Unit) = hookEvent(handler)
}
