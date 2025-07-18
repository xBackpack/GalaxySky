package me.xbackpack.galaxysky.item.api

import me.xbackpack.galaxysky.common.Builder
import me.xbackpack.galaxysky.message.Message
import me.xbackpack.galaxysky.service.LocationService
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

@ItemDsl
data class Item(
    val root: ItemBuilder,
) : Builder<ItemStack> {
    fun configure(builder: ItemBuilder.() -> Unit) = this.also { root.apply(builder) }

    override fun build() = root.build()

    companion object {
        fun create(
            name: Message,
            type: Material,
            region: LocationService.Region,
            id: String,
            builder: ItemBuilder.() -> Unit,
        ) = Item(ItemBuilder(name, type, region, id).apply(builder))
    }
}
