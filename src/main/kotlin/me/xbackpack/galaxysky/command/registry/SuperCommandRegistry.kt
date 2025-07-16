package me.xbackpack.galaxysky.command.registry

import me.xbackpack.galaxysky.command.api.Command
import me.xbackpack.galaxysky.common.Registry

object SuperCommandRegistry : Registry<Command> {
    override fun init(): List<Command> {
        val cmds = mutableListOf<Command>()

        cmds.addAll(PlayerCommandRegistry.init())
        cmds.addAll(StaffCommandRegistry.init())

        return cmds
    }
}
