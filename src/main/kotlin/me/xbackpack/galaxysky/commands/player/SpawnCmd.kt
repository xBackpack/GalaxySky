package me.xbackpack.galaxysky.commands.player

import me.xbackpack.galaxysky.commands.commandTypes.TeleportCommand
import me.xbackpack.galaxysky.util.Worlds

class SpawnCmd : TeleportCommand {
    override val commandName = "spawn"
    override val description = "Teleports the player to the server spawn"

    override val location = Worlds.spawnLocation
}
