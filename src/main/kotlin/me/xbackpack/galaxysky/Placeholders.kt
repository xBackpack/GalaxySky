package me.xbackpack.galaxysky

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.OfflinePlayer

class Placeholders : PlaceholderExpansion() {
    override fun getAuthor() = "xBackpack"

    override fun getIdentifier() = "galaxysky"

    override fun getVersion() = GalaxySky.version

    override fun persist() = true

    override fun onRequest(
        player: OfflinePlayer?,
        placeholder: String,
    ) = when (placeholder) {
        "ip" -> "galaxysky.minehut.gg"
        "shop" -> "galaxysky.tebex.io"
        "shop_link" -> "https://galaxysky.tebex.io"
        "discord" -> "https://discord.gg/GNJKTfZGrR"
        else -> null
    }
}
