package me.xbackpack.galaxysky.message

import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration

@MessageDSL
interface Stylable {
    var colour: TextColor?
    val decorations: MutableMap<TextDecoration, Boolean>

    fun colour(newColour: NamedTextColor)

    fun hex(hexString: String)

    fun bold(value: Boolean = true)

    fun italic(value: Boolean = true)

    fun underlined(value: Boolean = true)

    fun strikethrough(value: Boolean = true)

    fun obfuscated(value: Boolean = true)
}
