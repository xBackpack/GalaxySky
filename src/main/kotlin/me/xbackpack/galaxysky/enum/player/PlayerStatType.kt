package me.xbackpack.galaxysky.enum.player

import me.xbackpack.galaxysky.GalaxySky
import org.bukkit.NamespacedKey

enum class PlayerStatType(
    val key: NamespacedKey,
) {
    DEATHS(GalaxySky.createKey("deaths")),
    KILLS(GalaxySky.createKey("kills")),
    BLOCKS_MINED(GalaxySky.createKey("blocks")),
    PLAYTIME(GalaxySky.createKey("playtime")),
}
