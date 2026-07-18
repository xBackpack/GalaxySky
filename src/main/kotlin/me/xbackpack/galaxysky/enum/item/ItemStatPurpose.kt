package me.xbackpack.galaxysky.enum.item

import me.xbackpack.galaxysky.enum.Colour

enum class ItemStatPurpose(
    val colour: Colour,
) {
    OFFENSE(Colour.RED),
    DEFENSE(Colour.GREEN),
    UTILITY(Colour.AQUA),
}
