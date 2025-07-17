package me.xbackpack.galaxysky.item

import me.xbackpack.galaxysky.message.Message
import me.xbackpack.galaxysky.service.LocationService
import org.bukkit.Material

@ItemDsl
data class Item(
    val root: ItemBuilder,
) {
    fun configure(builder: ItemBuilder.() -> Unit) = this.also { root.apply(builder) }

    companion object {
        fun create(
            name: Message,
            type: Material,
            region: LocationService.Region,
            builder: ItemBuilder.() -> Unit,
        ) = ItemBuilder(name, type, region).apply(builder)
    }
}
