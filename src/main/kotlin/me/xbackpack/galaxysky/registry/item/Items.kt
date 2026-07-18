package me.xbackpack.galaxysky.registry.item

import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.api.util.item
import me.xbackpack.galaxysky.api.util.message
import me.xbackpack.galaxysky.enum.Colour
import me.xbackpack.galaxysky.enum.item.ItemRegion
import me.xbackpack.galaxysky.enum.item.ItemType
import org.bukkit.Material

object Items {
    val AFK_TOKEN =
        item(
            name = Message.name("AFK Token", Colour.LIGHT_PURPLE, true),
            material = Material.AMETHYST_SHARD,
            type = ItemType.ITEM,
            region = ItemRegion.BAYSIDE_BEACH,
            id = "afk_token",
        ) {
            description +=
                message {
                    text("Exchange for rewards at the AFK Shop!") {
                        colour(Colour.GREY)
                    }
                }
        }
}
