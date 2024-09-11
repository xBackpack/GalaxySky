package me.xbackpack.galaxysky.commands.player

import me.clip.placeholderapi.PlaceholderAPI
import me.xbackpack.galaxysky.commands.commandTypes.MessageCommand
import me.xbackpack.galaxysky.util.PluginUtilities
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor

class ShopCmd : MessageCommand {
    companion object {
        private val link = PlaceholderAPI.setPlaceholders(null, "%galaxysky_shop_link%")
    }

    override val commandName = "shop"
    override val description = "Returns the shop link"
    override val aliases = arrayListOf("store", "webstore")

    override val message =
        Component
            .text("The GalaxySky webstore is: ", NamedTextColor.LIGHT_PURPLE)
            .append(
                Component
                    .text(link)
                    .clickEvent(ClickEvent.openUrl(link))
                    .hoverEvent(PluginUtilities.openLink),
            )
}
