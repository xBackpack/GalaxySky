package me.xbackpack.galaxysky.item.api

import me.xbackpack.galaxysky.GalaxySky
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.NamespacedKey

@ItemDsl
enum class StatType(
    val statName: String,
    val purpose: StatPurpose,
    val key: NamespacedKey,
) {
    BREAKING_POWER(
        "Breaking Power",
        StatPurpose.UTILITY,
        GalaxySky.createKey("breaking_power"),
    ),
    MINING_SPEED(
        "Mining Speed",
        StatPurpose.UTILITY,
        GalaxySky.createKey("mining_speed"),
    ),
    ORE_FORTUNE(
        "Ore Fortune",
        StatPurpose.UTILITY,
        GalaxySky.createKey("ore_fortune"),
    ),
    ;

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
