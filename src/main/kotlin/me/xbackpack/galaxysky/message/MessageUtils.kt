package me.xbackpack.galaxysky.message

import io.papermc.paper.datacomponent.item.ItemLore
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.command.CommandSender

fun TextComponent.applyStyle(style: Stylable): TextComponent {
    var result = this.color(style.internalColour)

    style.internalDecorations.forEach { decoration ->
        result = result.decoration(decoration, TextDecoration.State.TRUE)
    }

    return result
}

fun ItemLore.Builder.addMessage(builder: MessageBuilder.() -> Unit) =
    addLines(
        MessageBuilder()
            .apply(builder)
            .toLore()
            .map { it.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE) },
    )

fun CommandSender.sendMessage(message: Message) {
    sendMessage(message.root)
}
