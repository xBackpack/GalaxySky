package me.xbackpack.galaxysky.commands.player

import me.clip.placeholderapi.PlaceholderAPI
import me.xbackpack.galaxysky.util.commandTypes.MessageCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandSender

class ApplyCmd : MessageCommand {
    override val commandName = "apply"
    override val description = "Links to the staff application"
    override val requiresPlayer = true

    private val link = PlaceholderAPI.setPlaceholders(null, "%galaxysky_discord%")

    override val message =
        Component
            .text("Apply on our discord!\nLink: ")
            .append(Component.text(link).clickEvent(ClickEvent.openUrl(link)))
            .color(NamedTextColor.LIGHT_PURPLE)

    override fun executeCommand(
        sender: CommandSender,
        args: List<String>,
    ) {
        sendMessage(sender)
    }
}
