package me.xbackpack.galaxysky.command.registry

import me.xbackpack.galaxysky.command.api.Command
import me.xbackpack.galaxysky.common.RegistrySupplier

object SuperCommandRegistry : RegistrySupplier<Command> {
    override fun get(): List<Command> {
        val cmds = mutableListOf<Command>()

        cmds.addAll(PlayerCommandRegistry.get())
        cmds.addAll(StaffCommandRegistry.get())

        return cmds
    }
}
