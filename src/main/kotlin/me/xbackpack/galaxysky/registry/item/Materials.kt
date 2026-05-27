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
            val COMPACT_COBBLESTONE =
                Item.create(
                    Message.name("Compact Cobblestone", NamedTextColor.GRAY),
                    Material.STONE,
                    ItemType.MATERIAL,
                    ItemRegion.BAYSIDE_BEACH,
                    "compact_cobblestone",
                ) { glowing = true }

            val COMPACT_COAL =
                Item.create(
                    Message.name("Compact Coal", NamedTextColor.DARK_GRAY),
                    Material.COAL_BLOCK,
                    ItemType.MATERIAL,
                    ItemRegion.BAYSIDE_BEACH,
                    "compact_coal",
                ) { glowing = true }

            val COMPACT_COPPER =
                Item.create(
                    Message.name("Compact Copper", NamedTextColor.GOLD),
                    Material.COPPER_BLOCK,
                    ItemType.MATERIAL,
                    ItemRegion.BAYSIDE_BEACH,
                    "compact_copper",
                ) { glowing = true }

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

    object CrimsonCove {
        val QUARTZ =
            Item.create(
                Message.name("Quartz"),
                Material.QUARTZ,
                ItemType.MATERIAL,
                ItemRegion.CRIMSON_COVE,
                "quartz",
            )

        val OBSIDIAN =
            Item.create(
                Message.name("Obsidian", NamedTextColor.DARK_GRAY),
                Material.OBSIDIAN,
                ItemType.MATERIAL,
                ItemRegion.CRIMSON_COVE,
                "obsidian",
            )

        val NETHERITE_SCRAP =
            Item.create(
                Message.name("Netherite Scrap", NamedTextColor.GRAY),
                Material.NETHERITE_SCRAP,
                ItemType.MATERIAL,
                ItemRegion.CRIMSON_COVE,
                "netherite_scrap",
            )

        val REDSTONE =
            Item.create(
                Message.name("Redstone", NamedTextColor.DARK_RED),
                Material.REDSTONE,
                ItemType.MATERIAL,
                ItemRegion.CRIMSON_COVE,
                "redstone",
            )

        val MAGMA =
            Item.create(
                Message.name("Magma", NamedTextColor.GOLD),
                Material.MAGMA_CREAM,
                ItemType.MATERIAL,
                ItemRegion.CRIMSON_COVE,
                "magma",
            )

        val NETHERITE =
            Item.create(
                Message.name("Netherite", NamedTextColor.GRAY),
                Material.NETHERITE_INGOT,
                ItemType.MATERIAL,
                ItemRegion.CRIMSON_COVE,
                "netherite",
            )

        object Strengthened {
            val STRENGTHENED_QUARTZ =
                Item.create(
                    Message.name("Strengthened Quartz"),
                    Material.QUARTZ,
                    ItemType.MATERIAL,
                    ItemRegion.CRIMSON_COVE,
                    "strengthened_quartz",
                ) { glowing = true }

            val STRENGTHENED_OBSIDIAN =
                Item.create(
                    Message.name("Strengthened Obsidian", NamedTextColor.DARK_GRAY),
                    Material.OBSIDIAN,
                    ItemType.MATERIAL,
                    ItemRegion.CRIMSON_COVE,
                    "strengthened_obsidian",
                ) { glowing = true }

            val STRENGTHENED_NETHERITE_SCRAP =
                Item.create(
                    Message.name("Strengthened Netherite Scrap", NamedTextColor.GRAY),
                    Material.NETHERITE_SCRAP,
                    ItemType.MATERIAL,
                    ItemRegion.CRIMSON_COVE,
                    "strengthened_netherite_scrap",
                ) { glowing = true }

            val STRENGTHENED_REDSTONE =
                Item.create(
                    Message.name("Strengthened Redstone", NamedTextColor.DARK_RED),
                    Material.REDSTONE,
                    ItemType.MATERIAL,
                    ItemRegion.CRIMSON_COVE,
                    "strengthened_redstone",
                ) { glowing = true }

            val STRENGTHENED_MAGMA =
                Item.create(
                    Message.name("Strengthened Magma", NamedTextColor.GOLD),
                    Material.MAGMA_CREAM,
                    ItemType.MATERIAL,
                    ItemRegion.CRIMSON_COVE,
                    "strengthened_magma",
                ) { glowing = true }

            val STRENGTHENED_NETHERITE =
                Item.create(
                    Message.name("Strengthened Netherite", NamedTextColor.GRAY),
                    Material.NETHERITE_INGOT,
                    ItemType.MATERIAL,
                    ItemRegion.CRIMSON_COVE,
                    "strengthened_netherite",
                ) { glowing = true }
        }

        object Compact {
            val COMPACT_QUARTZ =
                Item.create(
                    Message.name("Compact Quartz"),
                    Material.QUARTZ_BLOCK,
                    ItemType.MATERIAL,
                    ItemRegion.CRIMSON_COVE,
                    "compact_quartz",
                ) { glowing = true }

            val COMPACT_OBSIDIAN =
                Item.create(
                    Message.name("Compact Obsidian", NamedTextColor.DARK_GRAY),
                    Material.CRYING_OBSIDIAN,
                    ItemType.MATERIAL,
                    ItemRegion.CRIMSON_COVE,
                    "compact_obsidian",
                ) { glowing = true }

            val COMPACT_NETHERITE_SCRAP =
                Item.create(
                    Message.name("Compact Netherite Scrap", NamedTextColor.GRAY),
                    Material.ANCIENT_DEBRIS,
                    ItemType.MATERIAL,
                    ItemRegion.CRIMSON_COVE,
                    "compact_netherite_scrap",
                ) { glowing = true }

            val COMPACT_REDSTONE =
                Item.create(
                    Message.name("Compact Redstone", NamedTextColor.DARK_RED),
                    Material.REDSTONE_BLOCK,
                    ItemType.MATERIAL,
                    ItemRegion.CRIMSON_COVE,
                    "compact_redstone",
                ) { glowing = true }

            val COMPACT_MAGMA =
                Item.create(
                    Message.name("Compact Magma", NamedTextColor.GOLD),
                    Material.MAGMA_BLOCK,
                    ItemType.MATERIAL,
                    ItemRegion.CRIMSON_COVE,
                    "compact_magma",
                ) { glowing = true }

            val COMPACT_NETHERITE =
                Item.create(
                    Message.name("Compact Netherite", NamedTextColor.GRAY),
                    Material.NETHERITE_BLOCK,
                    ItemType.MATERIAL,
                    ItemRegion.CRIMSON_COVE,
                    "compact_netherite",
                ) { glowing = true }
        }
    }

    object VibrantVoid {
        val PURPUR =
            Item.create(
                Message.name("Purpur", NamedTextColor.LIGHT_PURPLE),
                Material.POPPED_CHORUS_FRUIT,
                ItemType.MATERIAL,
                ItemRegion.VIBRANT_VOID,
                "purpur",
            )

        val LAPIS =
            Item.create(
                Message.name("Lapis", NamedTextColor.BLUE),
                Material.LAPIS_LAZULI,
                ItemType.MATERIAL,
                ItemRegion.VIBRANT_VOID,
                "lapis",
            )

        val CHARGED_STONE =
            Item.create(
                Message.name("Charged Stone", NamedTextColor.GRAY),
                Material.AMETHYST_SHARD,
                ItemType.MATERIAL,
                ItemRegion.VIBRANT_VOID,
                "charged_stone",
            )

        val PRISMARINE =
            Item.create(
                Message.name("Prismarine", NamedTextColor.DARK_AQUA),
                Material.PRISMARINE_SHARD,
                ItemType.MATERIAL,
                ItemRegion.VIBRANT_VOID,
                "prismarine",
            )

        val OPAL =
            Item.create(
                Message.name("Opal"),
                Material.BONE_MEAL,
                ItemType.MATERIAL,
                ItemRegion.VIBRANT_VOID,
                "opal",
            )

        object Strengthened {
            val STRENGTHENED_PURPUR =
                Item.create(
                    Message.name("Strengthened Purpur", NamedTextColor.LIGHT_PURPLE),
                    Material.POPPED_CHORUS_FRUIT,
                    ItemType.MATERIAL,
                    ItemRegion.VIBRANT_VOID,
                    "strengthened_purpur",
                ) { glowing = true }

            val STRENGTHENED_LAPIS =
                Item.create(
                    Message.name("Strengthened Lapis", NamedTextColor.BLUE),
                    Material.LAPIS_LAZULI,
                    ItemType.MATERIAL,
                    ItemRegion.VIBRANT_VOID,
                    "strengthened_lapis",
                ) { glowing = true }

            val STRENGTHENED_CHARGED_STONE =
                Item.create(
                    Message.name("Strengthened Charged Stone", NamedTextColor.GRAY),
                    Material.AMETHYST_SHARD,
                    ItemType.MATERIAL,
                    ItemRegion.VIBRANT_VOID,
                    "strengthened_charged_stone",
                ) { glowing = true }

            val STRENGTHENED_PRISMARINE =
                Item.create(
                    Message.name("Strengthened Prismarine", NamedTextColor.DARK_AQUA),
                    Material.PRISMARINE_SHARD,
                    ItemType.MATERIAL,
                    ItemRegion.VIBRANT_VOID,
                    "strengthened_prismarine",
                ) { glowing = true }

            val STRENGTHENED_OPAL =
                Item.create(
                    Message.name("Strengthened Opal"),
                    Material.BONE_MEAL,
                    ItemType.MATERIAL,
                    ItemRegion.VIBRANT_VOID,
                    "strengthened_opal",
                ) { glowing = true }
        }

        object Compact {
            val COMPACT_PURPUR =
                Item.create(
                    Message.name("Compact Purpur", NamedTextColor.LIGHT_PURPLE),
                    Material.POPPED_CHORUS_FRUIT,
                    ItemType.MATERIAL,
                    ItemRegion.VIBRANT_VOID,
                    "compact_purpur",
                ) { glowing = true }

            val COMPACT_LAPIS =
                Item.create(
                    Message.name("Compact Lapis", NamedTextColor.BLUE),
                    Material.LAPIS_LAZULI,
                    ItemType.MATERIAL,
                    ItemRegion.VIBRANT_VOID,
                    "compact_lapis",
                ) { glowing = true }

            val COMPACT_CHARGED_STONE =
                Item.create(
                    Message.name("Compact Charged Stone", NamedTextColor.GRAY),
                    Material.AMETHYST_SHARD,
                    ItemType.MATERIAL,
                    ItemRegion.VIBRANT_VOID,
                    "compact_charged_stone",
                ) { glowing = true }

            val COMPACT_PRISMARINE =
                Item.create(
                    Message.name("Compact Prismarine", NamedTextColor.DARK_AQUA),
                    Material.PRISMARINE_SHARD,
                    ItemType.MATERIAL,
                    ItemRegion.VIBRANT_VOID,
                    "compact_prismarine",
                ) { glowing = true }

            val COMPACT_OPAL =
                Item.create(
                    Message.name("Compact Opal"),
                    Material.BONE_MEAL,
                    ItemType.MATERIAL,
                    ItemRegion.VIBRANT_VOID,
                    "compact_opal",
                ) { glowing = true }
        }
    }
}
