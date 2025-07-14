package me.xbackpack.galaxysky.util

import me.xbackpack.galaxysky.command.api.Command
import me.xbackpack.galaxysky.command.api.CommandBuilder
import me.xbackpack.galaxysky.command.api.CommandFunction
import me.xbackpack.galaxysky.command.api.util.UserCooldown

fun buildCommand(
    name: String,
    description: String,
    aliases: List<String>,
    cooldown: UserCooldown?,
    block: CommandFunction,
) = Command(
    CommandBuilder(name, block, cooldown),
    description,
    aliases,
    cooldown,
)
