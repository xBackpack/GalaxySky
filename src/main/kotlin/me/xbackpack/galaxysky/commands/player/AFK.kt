package me.xbackpack.galaxysky.commands.player

import me.xbackpack.galaxysky.util.Worlds
import me.xbackpack.galaxysky.util.commandTypes.TeleportCommand
import org.bukkit.Location

class AFK : TeleportCommand {
    override val commandName = "afk"
    override val description = "Teleports the player to the AFK area"
    override val location = Location(Worlds.world, -28.5, 102.0, 10.5, 90f, 0f)
}
