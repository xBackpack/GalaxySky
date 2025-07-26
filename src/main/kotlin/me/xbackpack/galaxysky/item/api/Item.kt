package me.xbackpack.galaxysky.item.api

import me.xbackpack.galaxysky.enum.item.ItemRegion
import me.xbackpack.galaxysky.enum.item.ItemType
import me.xbackpack.galaxysky.message.Message
import org.bukkit.Material

@ItemDsl
data class Item(
    val root: ItemBuilder,
) {
    fun configure(builder: ItemBuilder.() -> Unit) {
        root.apply(builder)
    }

    fun build() = root.build()

    companion object {
        fun create(
            name: Message,
            material: Material,
            type: ItemType,
            region: ItemRegion,
            id: String,
            builder: ItemBuilder.() -> Unit,
        ) = Item(ItemBuilder(name, material, type, region, id).apply(builder))
    }
}
