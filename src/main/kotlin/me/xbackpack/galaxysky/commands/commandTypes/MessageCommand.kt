package me.xbackpack.galaxysky.commands.commandTypes

import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender

interface MessageCommand : CommandBase {
    val message: Component

    fun sendMessage(sender: CommandSender) {
        sender.sendMessage(message)
    }

    interface Player : CommandBase {
        override val requiresPlayer: Boolean
            get() = true

        fun message(player: org.bukkit.entity.Player): Component

        fun sendMessage(player: org.bukkit.entity.Player) {
            player.sendMessage(message(player))
        }
    }
}
