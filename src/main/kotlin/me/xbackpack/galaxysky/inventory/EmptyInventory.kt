package me.xbackpack.galaxysky.inventory

import me.xbackpack.galaxysky.GalaxySky
import org.bukkit.inventory.InventoryHolder

class EmptyInventory private constructor() : InventoryHolder {
    private val inventory =
        GalaxySky.createInventory(this, 3) {
            text("Trash")
        }

    override fun getInventory() = inventory

    companion object {
        fun new() = EmptyInventory()
    }
}
