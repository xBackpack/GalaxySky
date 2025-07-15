package me.xbackpack.galaxysky.command.registry

import me.xbackpack.galaxysky.command.api.Command
import me.xbackpack.galaxysky.command.registry.staff.StaffTeleportCommandRegistry
import me.xbackpack.galaxysky.util.Registry

object StaffCommandRegistry : Registry<Command> {
    override fun init(): List<Command> {
        val cmds = mutableListOf<Command>()

        cmds.addAll(StaffTeleportCommandRegistry.init())

        return cmds
    }
}
