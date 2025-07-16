package me.xbackpack.galaxysky.message

import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration

@MessageDsl
class TextStyleBuilder : AbstractStylable() {
    override var colour: TextColor? = null
    override val decorations = mutableMapOf<TextDecoration, Boolean>()
}
