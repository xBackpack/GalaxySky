package me.xbackpack.galaxysky.service

import me.xbackpack.galaxysky.GalaxySky
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object ItemIdService {
    val key = NamespacedKey(GalaxySky.instance, "id")

    operator fun get(item: ItemStack) = item.persistentDataContainer[key, PersistentDataType.STRING]

    operator fun set(
        item: ItemStack,
        id: String?,
    ) {
        item.editPersistentDataContainer { pdc ->
            id?.let {
                pdc[key, PersistentDataType.STRING] = it
            } ?: pdc.remove(key)
        }
    }
}
