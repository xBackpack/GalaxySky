package me.xbackpack.galaxysky.api.command.types

import com.mojang.brigadier.arguments.ArgumentType
import me.xbackpack.galaxysky.api.command.Argument
import me.xbackpack.galaxysky.api.command.Cooldown
import me.xbackpack.galaxysky.api.command.Permission
import me.xbackpack.galaxysky.api.command.Requirement
import me.xbackpack.galaxysky.api.command.branches.OptionalArgument
import me.xbackpack.galaxysky.api.command.branches.Subcommand

interface Branch {
    val cooldown: Cooldown?

    val arguments: MutableList<Argument>
    val optionals: MutableList<OptionalArgument>
    val subcommands: MutableList<Subcommand>

    fun argument(
        name: String,
        type: ArgumentType<*>,
    ) {
        arguments += Argument(name, type)
    }

    fun optional(
        name: String,
        type: ArgumentType<*>,
        requirement: Requirement,
        permission: Permission? = null,
        factory: OptionalArgument.() -> Unit,
    ) {
        optionals += OptionalArgument(name, type, requirement, permission, factory)
    }
}
