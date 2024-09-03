package me.xbackpack.galaxysky.commands.player

import me.clip.placeholderapi.PlaceholderAPI
import me.xbackpack.galaxysky.commands.commandTypes.MessageCommand
import me.xbackpack.galaxysky.util.PluginUtilities
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor

class ApplyCmd : MessageCommand {
    companion object {
        private val link = PlaceholderAPI.setPlaceholders(null, "%galaxysky_discord%")
    }

    override val commandName = "apply"
    override val description = "Links to the staff application"

    override val message =
        Component
            .text("Apply on our discord!\nLink: ")
            .append(
                Component
                    .text(link)
                    .clickEvent(ClickEvent.openUrl(link))
                    .hoverEvent(PluginUtilities.openLink),
            ).color(NamedTextColor.LIGHT_PURPLE)
}
