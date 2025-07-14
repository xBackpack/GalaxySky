package me.xbackpack.galaxysky.command.impl

import me.xbackpack.galaxysky.command.api.CommandDSL
import me.xbackpack.galaxysky.command.api.util.UserCooldown
import me.xbackpack.galaxysky.util.buildCommand
import org.bukkit.Location
import org.bukkit.entity.Player

@CommandDSL
class TeleportCommand : BaseCommand {
    override lateinit var name: String
    override lateinit var description: String
    override var aliases: List<String> = emptyList()
    override var permission: String? = null
    override var cooldown: UserCooldown? = null
    lateinit var location: Location

    override fun create() =
        buildCommand(name, description, aliases, cooldown) { sender, _ ->
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
