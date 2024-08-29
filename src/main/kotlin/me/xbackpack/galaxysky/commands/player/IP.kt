package me.xbackpack.galaxysky.commands.player

import me.clip.placeholderapi.PlaceholderAPI
import me.xbackpack.galaxysky.util.commandTypes.MessageCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor

class IP : MessageCommand {
    override val commandName = "ip"
    override val description = "Returns the IP of the server"
    override val requiresPlayer = true

    private val ip = PlaceholderAPI.setPlaceholders(null, "%galaxysky_ip%")
    override val message =
        Component
            .text("The GalaxySky IP is: ")
            .append(
                Component
                    .text(ip)
                    .clickEvent(ClickEvent.copyToClipboard(ip))
                    .hoverEvent(HoverEvent.showText(Component.text("Click to copy to clipboard!"))),
            ).color(NamedTextColor.LIGHT_PURPLE)
}
