package me.xbackpack.galaxysky.command.registry

import me.xbackpack.galaxysky.command.registry.player.PlayerItemSupplyCommandRegistry
import me.xbackpack.galaxysky.command.registry.player.PlayerMessageCommandRegistry
import me.xbackpack.galaxysky.command.registry.player.PlayerTeleportCommandRegistry

object PlayerCommandRegistry {
    val commands =
        listOf(
            PlayerItemSupplyCommandRegistry.commands,
            PlayerMessageCommandRegistry.commands,
            PlayerTeleportCommandRegistry.commands,
        )
}
