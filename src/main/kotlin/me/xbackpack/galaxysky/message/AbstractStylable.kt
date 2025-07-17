package me.xbackpack.galaxysky.message

import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration

@MessageDsl
abstract class AbstractStylable : Stylable {
    override fun colour(newColour: TextColor) {
        colour = newColour
    }

    override fun hex(hexString: String) {
        TextColor
            .fromHexString(hexString)
            ?.let { colour = it }
            ?: error("Cannot get hex code from '$hexString'")
    }

    override fun bold(value: Boolean) {
        decorations[TextDecoration.BOLD] = value
    }

    override fun italic(value: Boolean) {
        decorations[TextDecoration.ITALIC] = value
    }

    override fun underlined(value: Boolean) {
        decorations[TextDecoration.UNDERLINED] = value
    }

    override fun strikethrough(value: Boolean) {
        decorations[TextDecoration.STRIKETHROUGH] = value
    }

    override fun obfuscated(value: Boolean) {
        decorations[TextDecoration.OBFUSCATED] = value
    }
}
