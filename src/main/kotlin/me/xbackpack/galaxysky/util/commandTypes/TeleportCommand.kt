package me.xbackpack.galaxysky.util.commandTypes

import org.bukkit.Location
import org.bukkit.entity.Player

interface TeleportCommand : CommandBase {
    override val requiresPlayer
        get() = true

    fun location(player: Player): Location
}
