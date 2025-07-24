package me.xbackpack.galaxysky.hook

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import me.xbackpack.galaxysky.GalaxySky
import me.xbackpack.galaxysky.enum.player.PlayerStatType
import me.xbackpack.galaxysky.service.FormattingService
import me.xbackpack.galaxysky.service.PDCService
import org.bukkit.OfflinePlayer

object PlaceholderHook : PlaceholderExpansion() {
    private const val SHOP_NAME = "galaxysky.tebex.io"

    const val SERVER_IP = "galaxysky.minehut.gg"
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
        "playtime" -> PDCService.Player.Stats[player, PlayerStatType.PLAYTIME].toString()
        "blocks" -> PDCService.Player.Stats[player, PlayerStatType.BLOCKS_MINED].toString()
        "kills" -> PDCService.Player.Stats[player, PlayerStatType.KILLS].toString()
        "deaths" -> PDCService.Player.Stats[player, PlayerStatType.DEATHS].toString()
        "playtime_formatted" -> FormattingService.shortenTime(PDCService.Player.Stats[player, PlayerStatType.PLAYTIME])
        "blocks_formatted" -> FormattingService.shortenStat(PDCService.Player.Stats[player, PlayerStatType.BLOCKS_MINED])
        "kills_formatted" -> FormattingService.shortenStat(PDCService.Player.Stats[player, PlayerStatType.KILLS])
        "deaths_formatted" -> FormattingService.shortenStat(PDCService.Player.Stats[player, PlayerStatType.DEATHS])
        else -> null
    }
}
