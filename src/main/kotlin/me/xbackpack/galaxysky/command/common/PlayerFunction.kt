package me.xbackpack.galaxysky.command.common

import org.bukkit.entity.Player

@CommandDsl
fun interface PlayerFunction<T> {
    fun execute(
        player: Player,
        args: List<Argument>,
    ): T
}
