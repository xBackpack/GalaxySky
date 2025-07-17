package me.xbackpack.galaxysky.item.api

import me.xbackpack.galaxysky.GalaxySky
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.NamespacedKey

@ItemDsl
enum class StatType(
    val statName: String,
    val purpose: StatPurpose,
    val operation: StatOperation,
    val key: NamespacedKey,
) {
    BREAKING_POWER(
        "Breaking Power",
        StatPurpose.PASSIVE,
        StatOperation.SET_VALUE,
        NamespacedKey(GalaxySky.instance, "breaking_power"),
    ),
    MINING_SPEED(
        "Mining Speed",
        StatPurpose.PASSIVE,
        StatOperation.ADD_VALUE,
        NamespacedKey(GalaxySky.instance, "mining_speed"),
    ),
    ORE_FORTUNE(
        "Ore Fortune",
        StatPurpose.PASSIVE,
        StatOperation.ADD_VALUE,
        NamespacedKey(GalaxySky.instance, "ore_fortune"),
    ),
    ;

    enum class StatOperation {
        ADD_VALUE,
        MINUS_VALUE,
        SET_VALUE,
    }

    enum class StatPurpose(
        val colour: NamedTextColor,
    ) {
        ACTIVE(NamedTextColor.RED),
        PASSIVE(NamedTextColor.GREEN),
    }
}
