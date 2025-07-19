package me.xbackpack.galaxysky.command.impl

import me.xbackpack.galaxysky.command.api.Command
import me.xbackpack.galaxysky.command.api.CommandArgument
import me.xbackpack.galaxysky.command.util.UserCooldown
import org.bukkit.entity.Player

class MiscPlayerCommand : BaseCommand {
    override lateinit var name: String
    override lateinit var description: String
    override var aliases: List<String> = emptyList()
    override var permission: String? = null
    override var cooldown: UserCooldown? = null
    lateinit var function: (player: Player, args: List<CommandArgument>) -> Unit

    override fun create() =
        Command
            .create(name, description, aliases, cooldown) { sender, args ->
                val player = sender as Player

                function(player, args)
            }.configure {
                playerOnly()

                permission(this@MiscPlayerCommand.permission)
            }

    companion object {
        fun create(builder: MiscPlayerCommand.() -> Unit) = MiscPlayerCommand().apply(builder).create()
    }
}
