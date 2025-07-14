package me.xbackpack.galaxysky.hook

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import me.xbackpack.galaxysky.GalaxySky
import org.bukkit.OfflinePlayer

object PlaceholderHook : PlaceholderExpansion() {
    const val SERVER_IP = "galaxysky.minehut.gg"
    private const val SHOP_NAME = "galaxysky.tebex.io"
    const val SHOP_LINK = "https://$SHOP_NAME"
    const val DISCORD = "https://discord.gg/GNJKTfZGrR"

    override fun getIdentifier() = GalaxySky.meta.name.lowercase()

    override fun getAuthor() = GalaxySky.meta.authors.first()

    override fun getVersion() = GalaxySky.meta.version

    override fun persist() = true

    override fun onRequest(
        player: OfflinePlayer?,
        placeholder: String,
    ) = when (placeholder) {
        "ip" -> SERVER_IP
        "shop_name" -> SHOP_NAME
        "shop_link" -> SHOP_LINK
        "discord" -> DISCORD
        else -> null
    }
}
