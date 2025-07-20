package me.xbackpack.galaxysky.command.registry.staff

import me.xbackpack.galaxysky.command.api.Command
import me.xbackpack.galaxysky.command.impl.BaseCommand
import me.xbackpack.galaxysky.command.impl.TeleportCommand
import me.xbackpack.galaxysky.common.RegistrySupplier
import me.xbackpack.galaxysky.service.LocationService

object StaffTeleportCommandRegistry : RegistrySupplier<Command> {
    override fun get(): List<Command> {
        val staffWorld =
            BaseCommand.create({ TeleportCommand() }) {
                name = "staffworld"
                description = "Teleports you to the staff world"
                location = LocationService.staffWorldSpawnLocation
                permission = "galaxysky.world.staff"
            }

        val staffArea =
            BaseCommand.create({ TeleportCommand() }) {
                name = "staffarea"
                description = "Teleports you to the staff area"
                location = LocationService.staffAreaLocation
                permission = "galaxysky.area.staff"
            }

        val builderArea =
            BaseCommand.create({ TeleportCommand() }) {
                name = "builderarea"
                description = "Teleports you to the builder area"
                location = LocationService.builderAreaLocation
                permission = "galaxysky.area.builder"
            }

        return listOf(staffWorld, staffArea, builderArea)
    }
}
