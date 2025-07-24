package me.xbackpack.galaxysky.item.api

import me.xbackpack.galaxysky.enum.item.ItemStatType
import org.bukkit.NamespacedKey

@ItemDsl
data class StatModifier(
    val modifierKey: NamespacedKey,
    val modifiedStats: Map<ItemStatType, Double>,
)
