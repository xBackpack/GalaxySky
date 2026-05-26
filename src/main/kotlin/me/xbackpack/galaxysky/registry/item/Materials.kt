package me.xbackpack.galaxysky.registry.item

import me.xbackpack.galaxysky.api.item.Item
import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.enum.item.ItemRegion
import me.xbackpack.galaxysky.enum.item.ItemType
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material

object Materials {
    object BaysideBeach {
        val COBBLESTONE =
            Item.create(
                Message.name("Cobblestone", NamedTextColor.GRAY),
                Material.COBBLESTONE,
                ItemType.MATERIAL,
                ItemRegion.BAYSIDE_BEACH,
                "cobblestone",
            )

        val COAL =
            Item.create(
                Message.name("Coal", NamedTextColor.DARK_GRAY),
                Material.COAL,
                ItemType.MATERIAL,
                ItemRegion.BAYSIDE_BEACH,
                "coal",
            )

        val COPPER =
            Item.create(
                Message.name("Copper", NamedTextColor.GOLD),
                Material.COPPER_INGOT,
                ItemType.MATERIAL,
                ItemRegion.BAYSIDE_BEACH,
                "copper",
            )

        val IRON =
            Item.create(
                Message.name("Iron"),
                Material.IRON_INGOT,
                ItemType.MATERIAL,
                ItemRegion.BAYSIDE_BEACH,
                "iron",
            )

        val GOLD =
            Item.create(
                Message.name("Gold", NamedTextColor.YELLOW),
                Material.GOLD_INGOT,
                ItemType.MATERIAL,
                ItemRegion.BAYSIDE_BEACH,
                "gold",
            )

        val DIAMOND =
            Item.create(
                Message.name("Diamond", NamedTextColor.AQUA),
                Material.DIAMOND,
                ItemType.MATERIAL,
                ItemRegion.BAYSIDE_BEACH,
                "diamond",
            )

        object Strengthened {
            val STRENGTHENED_COBBLESTONE =
                Item.create(
                    Message.name("Strengthened Cobblestone", NamedTextColor.GRAY),
                    Material.COBBLESTONE,
                    ItemType.MATERIAL,
                    ItemRegion.BAYSIDE_BEACH,
                    "strengthened_cobblestone",
                ) { glowing = true }

            val STRENGTHENED_COAL =
                Item.create(
                    Message.name("Strengthened Coal", NamedTextColor.DARK_GRAY),
                    Material.COAL,
                    ItemType.MATERIAL,
                    ItemRegion.BAYSIDE_BEACH,
                    "strengthened_coal",
                ) { glowing = true }

            val STRENGTHENED_COPPER =
                Item.create(
                    Message.name("Strengthened Copper", NamedTextColor.GOLD),
                    Material.COPPER_INGOT,
                    ItemType.MATERIAL,
                    ItemRegion.BAYSIDE_BEACH,
                    "strengthened_copper",
                ) { glowing = true }

            val STRENGTHENED_IRON =
                Item.create(
                    Message.name("Strengthened Iron"),
                    Material.IRON_INGOT,
                    ItemType.MATERIAL,
                    ItemRegion.BAYSIDE_BEACH,
                    "strengthened_iron",
                ) { glowing = true }

            val STRENGTHENED_GOLD =
                Item.create(
                    Message.name("Strengthened Gold", NamedTextColor.YELLOW),
                    Material.GOLD_INGOT,
                    ItemType.MATERIAL,
                    ItemRegion.BAYSIDE_BEACH,
                    "strengthened_gold",
                ) { glowing = true }

            val STRENGTHENED_DIAMOND =
                Item.create(
                    Message.name("Strengthened Diamond", NamedTextColor.AQUA),
                    Material.DIAMOND,
                    ItemType.MATERIAL,
                    ItemRegion.BAYSIDE_BEACH,
                    "strengthened_diamond",
                ) { glowing = true }
        }

        object Compact {
            val COMPACT_IRON =
                Item.create(
                    Message.name("Compact Iron"),
                    Material.IRON_BLOCK,
                    ItemType.MATERIAL,
                    ItemRegion.BAYSIDE_BEACH,
                    "compact_iron",
                ) { glowing = true }

            val COMPACT_GOLD =
                Item.create(
                    Message.name("Compact Gold", NamedTextColor.YELLOW),
                    Material.GOLD_BLOCK,
                    ItemType.MATERIAL,
                    ItemRegion.BAYSIDE_BEACH,
                    "compact_gold",
                ) { glowing = true }

            val COMPACT_DIAMOND =
                Item.create(
                    Message.name("Compact Diamond", NamedTextColor.AQUA),
                    Material.DIAMOND_BLOCK,
                    ItemType.MATERIAL,
                    ItemRegion.BAYSIDE_BEACH,
                    "compact_diamond",
                ) { glowing = true }
        }
    }
}
