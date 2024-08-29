package me.xbackpack.galaxysky.commands.player

import me.clip.placeholderapi.PlaceholderAPI
import me.xbackpack.galaxysky.util.commandTypes.PlayerMessageCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class PlaytimeCmd : PlayerMessageCommand {
    override val commandName = "playtime"
    override val description = "Returns the player's playtime"

    override fun message(player: Player): Component =
        Component.text(
            PlaceholderAPI.setPlaceholders(player, "You have %galaxysky_playtime% of playtime!"),
            NamedTextColor.GREEN,
        )

    override fun executeCommand(
        sender: CommandSender,
        args: List<String>,
    ) {
        sendMessage(sender as Player)
    }
}
