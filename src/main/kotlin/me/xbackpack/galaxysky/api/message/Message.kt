package me.xbackpack.galaxysky.api.message

import me.xbackpack.galaxysky.api.util.message
import me.xbackpack.galaxysky.enum.Colour
import net.kyori.adventure.text.Component

@MessageDsl
class Message : Customisable {
    override val children = mutableListOf<Component>()

    fun build() = Component.empty().append(children)

    companion object {
        fun name(
            text: String,
            colour: Colour = Colour.WHITE,
            bold: Boolean = false,
        ) = message {
            text(text) {
                colour(colour)
                if (bold) bold()
            }
        }

        fun empty() = message {}

        fun space() = message { children += Component.space() }

        fun newline() = message { children += Component.newline() }
    }
}
