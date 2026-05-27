package me.xbackpack.galaxysky.api.message

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

@MessageDsl
data class Message(
    val component: Component,
) {
    companion object {
        fun empty() = Message(Component.empty())

        fun name(
            text: String,
            colour: NamedTextColor = NamedTextColor.WHITE,
            bold: Boolean = false,
        ) = create {
            text(text) {
                colour(colour)
                if (bold) bold()
            }
        }

        fun space() = Message(Component.space())

        fun newline() = Message(Component.newline())

        fun create(builder: MessageBuilder.() -> Unit) = MessageBuilder().apply(builder).build()
    }
}
