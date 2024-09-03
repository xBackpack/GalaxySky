package me.xbackpack.galaxysky.commands.player

import me.clip.placeholderapi.PlaceholderAPI
import me.xbackpack.galaxysky.commands.commandTypes.MessageCommand
import me.xbackpack.galaxysky.util.PluginUtilities
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor

class IPCmd : MessageCommand {
    companion object {
        private val ip = PlaceholderAPI.setPlaceholders(null, "%galaxysky_ip%")
    }

    override val commandName = "ip"
    override val description = "Returns the IP of the server"

    override val message =
        Component
            .text("The GalaxySky IP is: ")
            .append(
                Component
                    .text(
                        ip,
                    ).clickEvent(ClickEvent.openUrl(ip))
                    .hoverEvent(PluginUtilities.openLink),
            ).color(NamedTextColor.LIGHT_PURPLE)
}
