package me.xbackpack.galaxysky.command.registry

object SuperCommandRegistry {
    val commands = PlayerCommandRegistry.commands + StaffCommandRegistry.commands
}
