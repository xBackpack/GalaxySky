package me.xbackpack.galaxysky.message

import io.papermc.paper.datacomponent.item.ItemLore
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.command.CommandSender

fun TextComponent.applyStyle(style: Stylable): TextComponent {
    var result = this.color(style.colour)

    style.decorations.forEach { (dec, enabled) ->
        result = result.decoration(dec, enabled)
    }

    return result
}

fun ItemLore.Builder.addLines(messages: List<Message>) = addLines(messages.map { it.root })

fun ItemLore.Builder.addMessage(builder: MessageBuilder.() -> Unit) =
    addLines(
        MessageBuilder()
            .apply(builder)
            .toLore()
            .map { it.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE) },
    )

fun CommandSender.sendMessage(
    message: Message,
    playDenialSound: Boolean = false,
) {
    sendMessage(message.root)

    if (playDenialSound) playSound(denialSound)
}

fun CommandSender.sendMessageAndSound(
    message: Message,
    sound: Sound,
) {
    sendMessage(message.root)
    playSound(sound)
}

private val denialSound = Sound.sound(Key.key("entity.enderman.teleport"), Sound.Source.MASTER, 0.5f, 0.5f)
