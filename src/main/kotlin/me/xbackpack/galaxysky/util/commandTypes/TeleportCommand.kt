package me.xbackpack.galaxysky.util.commandTypes

import me.xbackpack.galaxysky.util.CommandBase
import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

interface TeleportCommand : CommandBase {
    override val requiresPlayer: Boolean
        get() = true

    override fun command(
        sender: CommandSender,
        args: Array<String>,
    ) {
        val player = sender as Player

        player.teleport(getLocation())
    }

    fun getLocation(): Location
}
