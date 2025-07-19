package me.xbackpack.galaxysky.message

import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration

@MessageDsl
class TextStyleBuilder : Stylable {
    override var internalColour: TextColor? = null
    override val internalDecorations = mutableSetOf<TextDecoration>()
}
