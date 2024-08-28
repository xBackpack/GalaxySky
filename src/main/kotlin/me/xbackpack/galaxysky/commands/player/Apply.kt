package me.xbackpack.galaxysky.commands.player

import me.clip.placeholderapi.PlaceholderAPI
import me.xbackpack.galaxysky.util.commandTypes.MessageCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

class Apply : MessageCommand {
    override val commandName = "apply"
    override val description = "Links to the staff application"
    override val requiresPlayer = true
    override val message =
        Component.text(
            PlaceholderAPI.setPlaceholders(null, "Apply on our discord!\nLink: %galaxysky_discord%"),
            NamedTextColor.LIGHT_PURPLE,
        )
}
