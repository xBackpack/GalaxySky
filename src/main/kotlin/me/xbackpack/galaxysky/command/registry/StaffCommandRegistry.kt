package me.xbackpack.galaxysky.command.registry

import me.xbackpack.galaxysky.command.api.Command
import me.xbackpack.galaxysky.command.registry.staff.StaffTeleportCommandRegistry
import me.xbackpack.galaxysky.common.RegistrySupplier

object StaffCommandRegistry : RegistrySupplier<Command> {
    override fun get(): List<Command> {
        val cmds = mutableListOf<Command>()

        cmds.addAll(StaffTeleportCommandRegistry.get())

        return cmds
    }
}
