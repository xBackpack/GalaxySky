package me.xbackpack.galaxysky.registry.command

object CommandRegistry {
    val commands =
        PlayerCommandRegistry.commands +
            StaffCommandRegistry.commands +
            TestCommandRegistry.commands
}
