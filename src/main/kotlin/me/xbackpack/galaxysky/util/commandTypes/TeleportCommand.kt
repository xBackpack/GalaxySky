package me.xbackpack.galaxysky.util.commandTypes

import org.bukkit.Location
import org.bukkit.entity.Player

interface TeleportCommand : CommandBase {
    fun location(player: Player): Location

    override val requiresPlayer: Boolean
        get() = true

    fun teleport(player: Player) {
        player.teleport(location(player))
    }
}
