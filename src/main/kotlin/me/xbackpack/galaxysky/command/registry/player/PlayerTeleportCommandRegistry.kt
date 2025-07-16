package me.xbackpack.galaxysky.command.registry.player

import me.xbackpack.galaxysky.command.api.Command
import me.xbackpack.galaxysky.command.impl.TeleportCommand
import me.xbackpack.galaxysky.common.Registry
import me.xbackpack.galaxysky.service.LocationService

object PlayerTeleportCommandRegistry : Registry<Command> {
    override fun init(): List<Command> {
        // Worlds
        val spawnCmd =
            TeleportCommand.create {
                name = "spawn"
                description = "Teleports you to the server spawn"
                aliases = listOf("world", "stuck")
                location = LocationService.spawnLocation
            }

        val netherCmd =
            TeleportCommand.create {
                name = "nether"
                description = "Teleports you to the nether spawn"
                location = LocationService.netherSpawnLocation
                permission = "galaxysky.world.nether"
            }

        val endCmd =
            TeleportCommand.create {
                name = "end"
                description = "Teleports you to the end spawn"
                location = LocationService.endSpawnLocation
                permission = "galaxysky.world.end"
            }

        val aetherCmd =
            TeleportCommand.create {
                name = "aether"
                description = "Teleports you to the aether spawn"
                location = LocationService.aetherSpawnLocation
                permission = "galaxysky.world.aether"
            }

        // Areas
        val afkAreaCmd =
            TeleportCommand.create {
                name = "afk"
                description = "Teleports you to the afk area"
                location = LocationService.afkLocation
            }

        return listOf(spawnCmd, netherCmd, endCmd, aetherCmd, afkAreaCmd)
    }
}
