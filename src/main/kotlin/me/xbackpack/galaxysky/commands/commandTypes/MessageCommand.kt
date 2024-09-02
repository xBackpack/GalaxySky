package me.xbackpack.galaxysky.commands.commandTypes

import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender

interface MessageCommand : CommandBase {
    val message: Component

    fun sendMessage(sender: CommandSender) {
        sender.sendMessage(message)
    }
}
