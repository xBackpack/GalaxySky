package me.xbackpack.galaxysky.util.commandTypes

import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

interface TeleportCommand : CommandBase {
    override val requiresPlayer: Boolean
        get() = true

    val location: Location

    override fun command(
        sender: CommandSender,
        args: List<String>,
    ) {
        val player = sender as Player

        player.teleport(location)
    }
}
