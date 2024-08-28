package me.xbackpack.galaxysky.util.commandTypes

import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender

interface MessageCommand : CommandBase {
    val message: Component

    override fun command(
        sender: CommandSender,
        args: List<String>,
    ) {
        sender.sendMessage(message)
    }
}
