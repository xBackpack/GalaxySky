package me.xbackpack.galaxysky.enum.item

import me.xbackpack.galaxysky.GalaxySky
import me.xbackpack.galaxysky.api.item.ItemDsl
import org.bukkit.NamespacedKey

@ItemDsl
enum class ItemStatType(
    val statName: String,
    val purpose: ItemStatPurpose,
    val key: NamespacedKey,
) {
    BREAKING_POWER(
        "Breaking Power",
        ItemStatPurpose.UTILITY,
        GalaxySky.createKey("breaking_power"),
    ),
    MINING_SPEED(
        "Mining Speed",
        ItemStatPurpose.UTILITY,
        GalaxySky.createKey("mining_speed"),
    ),
    ORE_FORTUNE(
        "Ore Fortune",
        ItemStatPurpose.UTILITY,
        GalaxySky.createKey("ore_fortune"),
    ),
    ;

    companion object {
        val displayOrder =
            listOf(
                BREAKING_POWER,
                MINING_SPEED,
                ORE_FORTUNE,
            )
    }
}
