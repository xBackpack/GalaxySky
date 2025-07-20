package me.xbackpack.galaxysky.command.impl

import me.xbackpack.galaxysky.command.api.Command
import me.xbackpack.galaxysky.command.api.CommandArgument
import me.xbackpack.galaxysky.command.api.CommandBuilder
import me.xbackpack.galaxysky.command.util.UserCooldown
import org.bukkit.command.CommandSender

interface BaseCommand {
    var name: String
    var description: String
    var aliases: List<String>
    var permission: String?
    var cooldown: UserCooldown?
    val builder: (CommandSender, List<CommandArgument>) -> Unit
    val configuration: CommandBuilder.() -> Unit

    fun create() = Command.create(name, description, aliases, permission, cooldown, builder).configure(configuration)

    companion object {
        fun <T : BaseCommand> create(
            factory: () -> T,
            builder: T.() -> Unit,
        ) = factory().apply(builder).create()
    }
}
