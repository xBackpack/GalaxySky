package me.xbackpack.galaxysky.command.api

import org.bukkit.command.CommandSender

fun interface CommandFunction {
    fun perform(
        sender: CommandSender,
        args: List<CommandArgument>,
    )
}
