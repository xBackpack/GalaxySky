package me.xbackpack.galaxysky.util.commandTypes

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

interface PlayerMessageCommand : CommandBase {
    fun message(player: Player): Component

    override val requiresPlayer
        get() = true

    fun sendMessage(player: Player) {
        player.sendMessage(message(player))
    }
}
