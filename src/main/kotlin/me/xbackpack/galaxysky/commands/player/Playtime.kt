package me.xbackpack.galaxysky.commands.player

import me.clip.placeholderapi.PlaceholderAPI
import me.xbackpack.galaxysky.util.commandTypes.PlayerMessageCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Playtime : PlayerMessageCommand {
    override val commandName = "playtime"
    override val description = "Returns the player's playtime"

    override fun message(sender: CommandSender): Component =
        Component.text(
            PlaceholderAPI.setPlaceholders(sender as Player, "%galaxysky_playtime%"),
            NamedTextColor.GREEN,
        )
}
