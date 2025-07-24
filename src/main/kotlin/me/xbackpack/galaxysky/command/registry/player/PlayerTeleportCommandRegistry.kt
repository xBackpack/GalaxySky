package me.xbackpack.galaxysky.command.registry.player

import me.xbackpack.galaxysky.command.impl.BaseCommand
import me.xbackpack.galaxysky.command.impl.TeleportCommand
import me.xbackpack.galaxysky.service.LocationService

object PlayerTeleportCommandRegistry {
    val commands =
        listOf(
            BaseCommand.create({ TeleportCommand() }) {
                name = "spawn"
                description = "Teleports you to the server spawn"
                aliases = listOf("beach", "world", "stuck")
                location = LocationService.spawnLocation
            },
            BaseCommand.create({ TeleportCommand() }) {
                name = "nether"
                description = "Teleports you to the nether spawn"
                aliases = listOf("cove")
                location = LocationService.netherSpawnLocation
                permission = "galaxysky.world.nether"
            },
            BaseCommand.create({ TeleportCommand() }) {
                name = "end"
                description = "Teleports you to the end spawn"
                aliases = listOf("void")
                location = LocationService.endSpawnLocation
                permission = "galaxysky.world.end"
            },
            BaseCommand.create({ TeleportCommand() }) {
                name = "aether"
                description = "Teleports you to the aether spawn"
                aliases = listOf("sanctuary")
                location = LocationService.aetherSpawnLocation
                permission = "galaxysky.world.aether"
            },
            BaseCommand.create({ TeleportCommand() }) {
                name = "afk"
                description = "Teleports you to the afk area"
                location = LocationService.afkLocation
            },
        )
}
