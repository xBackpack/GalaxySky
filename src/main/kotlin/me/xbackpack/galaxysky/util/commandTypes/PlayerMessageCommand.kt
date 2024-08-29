package me.xbackpack.galaxysky.util.commandTypes

import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender

interface PlayerMessageCommand : CommandBase {
    fun message(sender: CommandSender): Component

    override val requiresPlayer
        get() = true

    override fun command(
        sender: CommandSender,
        args: List<String>,
    ) {
        sender.sendMessage(message(sender))
    }
}
