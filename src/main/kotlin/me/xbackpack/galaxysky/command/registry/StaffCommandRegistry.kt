package me.xbackpack.galaxysky.command.registry

import me.xbackpack.galaxysky.command.registry.staff.StaffItemSupplyCommandRegistry
import me.xbackpack.galaxysky.command.registry.staff.StaffTeleportCommandRegistry

object StaffCommandRegistry {
    val commands = StaffItemSupplyCommandRegistry.commands + StaffTeleportCommandRegistry.commands
}
