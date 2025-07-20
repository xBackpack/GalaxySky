package me.xbackpack.galaxysky.item.api

import me.xbackpack.galaxysky.message.Message
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player

fun Player.giveItem(item: Item) {
    inventory
        .addItem(item.build())
        .takeIf { it.isNotEmpty() }
        ?.let {
            sendActionBar(
                Message
                    .create {
                        text("Inventory Full!") {
                            colour(NamedTextColor.RED)
                        }
                    }.root,
            )
        }
}
