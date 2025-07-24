package me.xbackpack.galaxysky.command.registry

object SuperCommandRegistry {
    val commands =
        listOf(
            PlayerCommandRegistry.commands,
            StaffCommandRegistry.commands,
        )
}
