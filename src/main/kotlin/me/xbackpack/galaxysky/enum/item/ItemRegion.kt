package me.xbackpack.galaxysky.enum.item

import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor

enum class ItemRegion(
    val colour: TextColor,
) {
    BAYSIDE_BEACH(NamedTextColor.YELLOW),
    CRIMSON_COVE(NamedTextColor.RED),
    VIBRANT_VOID(NamedTextColor.LIGHT_PURPLE),
}
