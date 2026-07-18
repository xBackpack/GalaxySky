package me.xbackpack.galaxysky.api.message

import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration

@MessageDsl
class TextStyle : Stylable {
    override var internalColours: TextColor? = null
    override val internalDecorations = mutableSetOf<TextDecoration>()
}
