package me.xbackpack.galaxysky.command.registry

import me.xbackpack.galaxysky.command.api.Command
import me.xbackpack.galaxysky.command.registry.player.PlayerMessageCommandRegistry
import me.xbackpack.galaxysky.command.registry.player.PlayerMiscCommandRegistry
import me.xbackpack.galaxysky.command.registry.player.PlayerTeleportCommandRegistry
import me.xbackpack.galaxysky.common.Registry

object PlayerCommandRegistry : Registry<Command> {
    override fun init(): List<Command> {
        val cmds = mutableListOf<Command>()

        cmds.addAll(PlayerMessageCommandRegistry.init())
        cmds.addAll(PlayerMiscCommandRegistry.init())
        cmds.addAll(PlayerTeleportCommandRegistry.init())

        return cmds
    }
}
