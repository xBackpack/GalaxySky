package me.xbackpack.galaxysky.command.registry

import me.xbackpack.galaxysky.command.api.Command
import me.xbackpack.galaxysky.command.registry.staff.StaffTeleportCommandRegistry

object StaffCommandRegistry : CommandRegistry {
    override fun init(): List<Command> {
        val cmds = mutableListOf<Command>()

        cmds.addAll(StaffTeleportCommandRegistry.init())

        return cmds
    }
}
