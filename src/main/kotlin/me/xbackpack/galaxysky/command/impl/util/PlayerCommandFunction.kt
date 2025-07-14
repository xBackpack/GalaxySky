package me.xbackpack.galaxysky.command.impl.util

import me.xbackpack.galaxysky.command.api.CommandArgument
import org.bukkit.entity.Player

fun interface PlayerCommandFunction {
    fun perform(
        player: Player,
        args: List<CommandArgument>,
    )
}
