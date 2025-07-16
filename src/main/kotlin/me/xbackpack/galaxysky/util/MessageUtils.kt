package me.xbackpack.galaxysky.util

import me.xbackpack.galaxysky.message.Message
import me.xbackpack.galaxysky.message.Stylable
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.TextComponent
import org.bukkit.command.CommandSender

fun TextComponent.applyStyle(style: Stylable): TextComponent {
    var result = this.color(style.colour)

    style.decorations.forEach { (dec, enabled) ->
        result = result.decoration(dec, enabled)
    }

    return result
}

fun CommandSender.sendMessage(
    message: Message,
    playDenialSound: Boolean = false,
) {
    sendMessage(message.component)

    if (playDenialSound) playSound(denialSound)
}

fun CommandSender.sendMessageAndSound(
    message: Message,
    sound: Sound,
) {
    sendMessage(message.component)
    playSound(sound)
}

private val denialSound = Sound.sound(Key.key("entity.enderman.teleport"), Sound.Source.MASTER, 0.5f, 0.5f)
