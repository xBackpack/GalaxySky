package me.xbackpack.galaxysky.commands.player

import me.xbackpack.galaxysky.util.Worlds
import me.xbackpack.galaxysky.util.commandTypes.TeleportCommand
import org.bukkit.Location

class AFK : TeleportCommand {
    companion object {
        private val loc = Location(Worlds.world, -28.5, 102.0, 10.5, 90f, 0f)
    }

    override fun getLocation() = loc
}
