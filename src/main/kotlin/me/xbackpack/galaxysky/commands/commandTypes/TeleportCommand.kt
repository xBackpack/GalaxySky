package me.xbackpack.galaxysky.commands.commandTypes

import org.bukkit.Location
import org.bukkit.entity.Player

interface TeleportCommand : CommandBase {
    override val requiresPlayer
        get() = true

    val location: Location

    fun teleport(player: Player) {
        player.teleport(location)
    }
}
