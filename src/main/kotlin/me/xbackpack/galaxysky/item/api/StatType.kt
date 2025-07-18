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
        StatPurpose.UTILITY,
        StatOperation.SET_VALUE,
        GalaxySky.createKey("breaking_power"),
    ),
    MINING_SPEED(
        "Mining Speed",
        StatPurpose.UTILITY,
        StatOperation.ADD_VALUE,
        GalaxySky.createKey("mining_speed"),
    ),
    ORE_FORTUNE(
        "Ore Fortune",
        StatPurpose.UTILITY,
        StatOperation.ADD_VALUE,
        GalaxySky.createKey("ore_fortune"),
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
        OFFENSE(NamedTextColor.RED),
        DEFENSE(NamedTextColor.GREEN),
        UTILITY(NamedTextColor.AQUA),
    }

    companion object {
        val displayOrder =
            listOf(
                BREAKING_POWER,
                MINING_SPEED,
                ORE_FORTUNE,
            )
    }
}
