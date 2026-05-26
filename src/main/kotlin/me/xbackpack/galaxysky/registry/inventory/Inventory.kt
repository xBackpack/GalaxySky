package me.xbackpack.galaxysky.registry.inventory

import me.xbackpack.galaxysky.GalaxySky
import me.xbackpack.galaxysky.api.message.MessageBuilder
import org.bukkit.inventory.InventoryHolder

class Inventory private constructor(
    title: MessageBuilder.() -> Unit,
) : InventoryHolder {
    private val inventory =
        GalaxySky.createInventory(this, 3, title)

    override fun getInventory() = inventory

    companion object {
        fun new(title: MessageBuilder.() -> Unit) = Inventory(title)
    }
}
