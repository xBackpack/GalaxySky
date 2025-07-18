package me.xbackpack.galaxysky.command.impl

import me.xbackpack.galaxysky.command.api.Command
import me.xbackpack.galaxysky.command.api.CommandDsl
import me.xbackpack.galaxysky.command.util.UserCooldown
import org.bukkit.Location
import org.bukkit.entity.Player

@CommandDsl
class TeleportCommand : BaseCommand {
    override lateinit var name: String
    override lateinit var description: String
    override var aliases: List<String> = emptyList()
    override var permission: String? = null
    override var cooldown: UserCooldown? = null
    lateinit var location: Location

    override fun create() =
        Command
            .create(name, description, aliases, cooldown) { sender, _ ->
                val player = sender as Player

                player.teleport(location)
            }.configure {
                playerOnly()

                permission(this@TeleportCommand.permission)

                staffCanUseOnOthers()
            }

    companion object {
        fun create(builder: TeleportCommand.() -> Unit) = TeleportCommand().apply(builder).create()
    }
}
