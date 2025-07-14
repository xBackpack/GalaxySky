package me.xbackpack.galaxysky.command.registry

import me.xbackpack.galaxysky.command.api.Command

object SuperCommandRegistry : CommandRegistry {
    override fun init(): List<Command> {
        val cmds = mutableListOf<Command>()

        cmds.addAll(PlayerCommandRegistry.init())
        cmds.addAll(StaffCommandRegistry.init())

        return cmds
    }
}
