package me.xbackpack.galaxysky.enum.item

import net.kyori.adventure.text.format.NamedTextColor

enum class ItemStatPurpose(
    val colour: NamedTextColor,
) {
    OFFENSE(NamedTextColor.RED),
    DEFENSE(NamedTextColor.GREEN),
    UTILITY(NamedTextColor.AQUA),
}
