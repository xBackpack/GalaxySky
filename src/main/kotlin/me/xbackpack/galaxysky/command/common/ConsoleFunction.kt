package me.xbackpack.galaxysky.command.common

import org.bukkit.command.ConsoleCommandSender

@CommandDsl
fun interface ConsoleFunction<T> {
    fun execute(
        console: ConsoleCommandSender,
        args: List<Argument>,
    ): T
}
