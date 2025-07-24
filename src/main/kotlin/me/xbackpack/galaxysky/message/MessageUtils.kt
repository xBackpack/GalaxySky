package me.xbackpack.galaxysky.message

import org.bukkit.command.CommandSender

fun CommandSender.sendMessage(message: Message) {
    sendMessage(message.root)
}
