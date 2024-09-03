package me.xbackpack.galaxysky.commands.player

import me.xbackpack.galaxysky.commands.commandTypes.TeleportCommand
import me.xbackpack.galaxysky.util.Worlds
import org.bukkit.Location

class AFKCmd : TeleportCommand {
    companion object {
        private val loc = Location(Worlds.world, -28.5, 102.0, 10.5, 90f, 0f)
    }

    override val commandName = "afk"
    override val description = "Teleports the player to the AFK area"

    override val location = loc
}
