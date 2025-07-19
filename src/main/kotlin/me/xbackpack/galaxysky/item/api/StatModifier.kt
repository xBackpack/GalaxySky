package me.xbackpack.galaxysky.item.api

import org.bukkit.NamespacedKey

@ItemDsl
data class StatModifier(
    val modifierKey: NamespacedKey,
    val modifiedStats: MutableMap<StatType, Double>,
)
