package me.xbackpack.galaxysky.item

import org.bukkit.NamespacedKey

data class StatModifier(
    val type: StatType,
    val modifierKey: NamespacedKey,
    val bonus: Int,
    val operation: StatType.StatOperation,
)
