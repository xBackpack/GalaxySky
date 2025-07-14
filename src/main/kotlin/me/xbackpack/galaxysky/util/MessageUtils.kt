package me.xbackpack.galaxysky.util

import me.xbackpack.galaxysky.message.MessageBuilder
import me.xbackpack.galaxysky.message.Stylable
import net.kyori.adventure.text.TextComponent

fun TextComponent.applyStyle(style: Stylable): TextComponent {
    var result = this.color(style.colour)

    style.decorations.forEach { (dec, enabled) ->
        result = result.decoration(dec, enabled)
    }

    return result
}

fun buildMessage(builder: MessageBuilder.() -> Unit) = MessageBuilder().apply(builder).build()
