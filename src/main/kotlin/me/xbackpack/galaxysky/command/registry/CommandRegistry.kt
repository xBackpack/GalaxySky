package me.xbackpack.galaxysky.command.registry

import me.xbackpack.galaxysky.command.api.Command

interface CommandRegistry {
    fun init(): List<Command>
}
