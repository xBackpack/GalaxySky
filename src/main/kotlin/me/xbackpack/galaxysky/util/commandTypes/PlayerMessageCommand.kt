package me.xbackpack.galaxysky.util.commandTypes

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

interface PlayerMessageCommand : CommandBase {
    override val requiresPlayer: Boolean
        get() = true

    fun message(player: Player): Component
}
