package me.xbackpack.galaxysky.enum.item

import me.xbackpack.galaxysky.GalaxySky
import me.xbackpack.galaxysky.api.item.ItemDsl
import org.bukkit.NamespacedKey

@ItemDsl
enum class ItemStatType(
    val statName: String,
    val purpose: ItemStatPurpose,
    val key: NamespacedKey,
    val key2: NamespacedKey = key,
) {
    BREAKING_POWER(
        "Breaking Power",
        ItemStatPurpose.UTILITY,
        GalaxySky.createKey("breaking_power"),
    ),
    MINING_SPEED(
        "Mining Speed",
        ItemStatPurpose.UTILITY,
        GalaxySky.createKey("mining_speed_add"),
        GalaxySky.createKey("mining_speed_mult"),
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
