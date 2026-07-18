package me.xbackpack.galaxysky.api.message

import me.xbackpack.galaxysky.enum.Colour
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration

interface Stylable {
    var internalColours: TextColor?
    val internalDecorations: MutableSet<TextDecoration>

    fun colour(newColour: Colour) {
        internalColours = newColour.colour
    }

    fun hex(hexString: String) {
        TextColor
            .fromHexString(hexString)
            ?.let { internalColours = it }
            ?: error("Cannot get hex code from '$hexString'")
    }

    fun bold() {
        internalDecorations.add(TextDecoration.BOLD)
    }

    fun italic() {
        internalDecorations.add(TextDecoration.ITALIC)
    }

    fun underlined() {
        internalDecorations.add(TextDecoration.UNDERLINED)
    }

    fun strikethrough() {
        internalDecorations.add(TextDecoration.STRIKETHROUGH)
    }

    fun obfuscated() {
        internalDecorations.add(TextDecoration.OBFUSCATED)
    }
}
