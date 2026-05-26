package me.xbackpack.galaxysky.enum.item

import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor

enum class ItemRegion(
    val displayName: String,
    val colour: TextColor,
) {
    BAYSIDE_BEACH("Bayside Beach", NamedTextColor.YELLOW),
    CRIMSON_COVE("Crimson Cove", NamedTextColor.RED),
    VIBRANT_VOID("Vibrant Void", NamedTextColor.LIGHT_PURPLE),
}
