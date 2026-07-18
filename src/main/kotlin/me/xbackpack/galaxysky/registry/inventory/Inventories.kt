package me.xbackpack.galaxysky.registry.inventory

import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.api.util.inventory

object Inventories {
    val BIN =
        inventory(
            title = Message.name("Bin"),
            rows = 3,
            fillGrey = false,
        ) {}
}
