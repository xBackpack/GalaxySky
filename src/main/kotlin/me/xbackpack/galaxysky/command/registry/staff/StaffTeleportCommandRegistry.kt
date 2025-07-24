package me.xbackpack.galaxysky.command.registry.staff

import me.xbackpack.galaxysky.command.impl.BaseCommand
import me.xbackpack.galaxysky.command.impl.TeleportCommand
import me.xbackpack.galaxysky.service.LocationService

object StaffTeleportCommandRegistry {
    val commands =
        listOf(
            BaseCommand.create({ TeleportCommand() }) {
                name = "staffworld"
                description = "Teleports you to the staff world"
                location = LocationService.staffWorldSpawnLocation
                permission = "galaxysky.world.staff"
            },
            BaseCommand.create({ TeleportCommand() }) {
                name = "staffarea"
                description = "Teleports you to the staff area"
                location = LocationService.staffAreaLocation
                permission = "galaxysky.area.staff"
            },
            BaseCommand.create({ TeleportCommand() }) {
                name = "builderarea"
                description = "Teleports you to the builder area"
                location = LocationService.builderAreaLocation
                permission = "galaxysky.area.builder"
            },
        )
}
