package me.xbackpack.galaxysky.api.command.branches

import me.xbackpack.galaxysky.api.command.CommandDsl
import me.xbackpack.galaxysky.api.command.Cooldown
import me.xbackpack.galaxysky.api.command.Permission
import me.xbackpack.galaxysky.api.command.Requirement
import me.xbackpack.galaxysky.api.command.types.Branch
import me.xbackpack.galaxysky.api.util.ConsoleFunction
import me.xbackpack.galaxysky.api.util.PlayerFunction

@CommandDsl
class Subcommand(
    override val name: String,
    override val requirement: Requirement,
    override val permission: Permission?,
    override val cooldown: Cooldown?,
    override val parent: Branch?,
) : Branch {
    override val arguments = parent?.arguments ?: mutableListOf()
    override var optional: OptionalArgument? = null
    override val subcommands = mutableListOf<Subcommand>()
    override val playerFunctions = mutableListOf<PlayerFunction>()
    override val consoleFunctions = mutableListOf<ConsoleFunction>()
}
