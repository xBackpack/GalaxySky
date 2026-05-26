package me.xbackpack.galaxysky.api.item

import me.xbackpack.galaxysky.enum.item.ItemStatType
import org.bukkit.NamespacedKey

@ItemDsl
data class StatModifier(
    val modifierKey: NamespacedKey,
    val modifiedStats: Map<ItemStatType, Int>,
)
