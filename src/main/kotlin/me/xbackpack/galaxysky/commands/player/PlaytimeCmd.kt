package me.xbackpack.galaxysky.commands.player

import me.clip.placeholderapi.PlaceholderAPI
import me.xbackpack.galaxysky.util.commandTypes.PlayerMessageCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player

class PlaytimeCmd : PlayerMessageCommand {
    companion object {
        private fun getPlaytime(player: Player): String = PlaceholderAPI.setPlaceholders(player, "%galaxysky_playtime%")
    }

    override val commandName = "playtime"
    override val description = "Returns the player's playtime"

    override fun message(player: Player): Component =
        Component.text(
            "You have ${getPlaytime(player)} of playtime!",
            NamedTextColor.GREEN,
        )
}
