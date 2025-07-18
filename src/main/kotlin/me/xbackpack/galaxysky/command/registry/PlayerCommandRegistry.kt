package me.xbackpack.galaxysky.command.registry

import me.xbackpack.galaxysky.command.api.Command
import me.xbackpack.galaxysky.command.registry.player.PlayerMessageCommandRegistry
import me.xbackpack.galaxysky.command.registry.player.PlayerMiscCommandRegistry
import me.xbackpack.galaxysky.command.registry.player.PlayerTeleportCommandRegistry
import me.xbackpack.galaxysky.common.RegistrySupplier

object PlayerCommandRegistry : RegistrySupplier<Command> {
    override fun get(): List<Command> {
        val cmds = mutableListOf<Command>()

        cmds.addAll(PlayerMessageCommandRegistry.get())
        cmds.addAll(PlayerMiscCommandRegistry.get())
        cmds.addAll(PlayerTeleportCommandRegistry.get())

        return cmds
    }
}
