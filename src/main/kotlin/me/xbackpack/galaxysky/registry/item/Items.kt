package me.xbackpack.galaxysky.registry.item

import me.xbackpack.galaxysky.api.item.Item
import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.enum.item.ItemRegion
import me.xbackpack.galaxysky.enum.item.ItemType
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material

object Items {
    val AFK_TOKEN =
        Item.create(
            Message.name("AFK Token", NamedTextColor.LIGHT_PURPLE, true),
            Material.AMETHYST_SHARD,
            ItemType.ITEM,
            ItemRegion.BAYSIDE_BEACH,
            "afk_token",
        ) {
            description +=
                Message.create {
                    text("Exchange for rewards at the AFK Shop!") {
                        colour(NamedTextColor.GRAY)
                    }
                }
        }
}
