package me.xbackpack.galaxysky.item.api

import org.bukkit.NamespacedKey

@ItemDsl
data class StatModifier(
    val type: StatType,
    val modifierKey: NamespacedKey,
    val bonus: Int,
    val operation: StatType.StatOperation,
)
