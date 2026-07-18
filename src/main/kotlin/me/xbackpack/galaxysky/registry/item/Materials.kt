package me.xbackpack.galaxysky.registry.item

import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.api.util.item
import me.xbackpack.galaxysky.enum.Colour
import me.xbackpack.galaxysky.enum.item.ItemRegion
import me.xbackpack.galaxysky.enum.item.ItemType
import org.bukkit.Material

object Materials {
    val COBBLESTONE =
        item(
            Message.name("Cobblestone", Colour.GREY),
            Material.COBBLESTONE,
            ItemType.MATERIAL,
            ItemRegion.BAYSIDE_BEACH,
            "cobblestone",
        )

    val COAL =
        item(
            Message.name("Coal", Colour.DARK_GREY),
            Material.COAL,
            ItemType.MATERIAL,
            ItemRegion.BAYSIDE_BEACH,
            "coal",
        )

    val COPPER =
        item(
            Message.name("Copper", Colour.GOLD),
            Material.COPPER_INGOT,
            ItemType.MATERIAL,
            ItemRegion.BAYSIDE_BEACH,
            "copper",
        )

    val IRON =
        item(
            Message.name("Iron"),
            Material.IRON_INGOT,
            ItemType.MATERIAL,
            ItemRegion.BAYSIDE_BEACH,
            "iron",
        )

    val GOLD =
        item(
            Message.name("Gold", Colour.YELLOW),
            Material.GOLD_INGOT,
            ItemType.MATERIAL,
            ItemRegion.BAYSIDE_BEACH,
            "gold",
        )

    val DIAMOND =
        item(
            Message.name("Diamond", Colour.AQUA),
            Material.DIAMOND,
            ItemType.MATERIAL,
            ItemRegion.BAYSIDE_BEACH,
            "diamond",
        )

    val QUARTZ =
        item(
            Message.name("Quartz"),
            Material.QUARTZ,
            ItemType.MATERIAL,
            ItemRegion.CRIMSON_COVE,
            "quartz",
        )

    val OBSIDIAN =
        item(
            Message.name("Obsidian", Colour.DARK_GREY),
            Material.OBSIDIAN,
            ItemType.MATERIAL,
            ItemRegion.CRIMSON_COVE,
            "obsidian",
        )

    val NETHERITE_SCRAP =
        item(
            Message.name("Netherite Scrap", Colour.GREY),
            Material.NETHERITE_SCRAP,
            ItemType.MATERIAL,
            ItemRegion.CRIMSON_COVE,
            "netherite_scrap",
        )

    val REDSTONE =
        item(
            Message.name("Redstone", Colour.DARK_RED),
            Material.REDSTONE,
            ItemType.MATERIAL,
            ItemRegion.CRIMSON_COVE,
            "redstone",
        )

    val MAGMA =
        item(
            Message.name("Magma", Colour.GOLD),
            Material.MAGMA_CREAM,
            ItemType.MATERIAL,
            ItemRegion.CRIMSON_COVE,
            "magma",
        )

    val NETHERITE =
        item(
            Message.name("Netherite", Colour.GREY),
            Material.NETHERITE_INGOT,
            ItemType.MATERIAL,
            ItemRegion.CRIMSON_COVE,
            "netherite",
        )

    val RUBY =
        item(
            Message.name("Ruby", Colour.RED),
            Material.EMERALD,
            ItemType.MATERIAL,
            ItemRegion.CRIMSON_COVE,
            "ruby",
            "ruby",
        )

    val SAPPHIRE =
        item(
            Message.name("Sapphire", Colour.BLUE),
            Material.EMERALD,
            ItemType.MATERIAL,
            ItemRegion.CRIMSON_COVE,
            "sapphire",
            "sapphire",
        )

    val PURPUR =
        item(
            name = Message.name("Purpur", Colour.LIGHT_PURPLE),
            material = Material.POPPED_CHORUS_FRUIT,
            type = ItemType.MATERIAL,
            region = ItemRegion.VIBRANT_VOID,
            id = "purpur",
        )

    val LAPIS =
        item(
            name = Message.name("Lapis", Colour.BLUE),
            material = Material.LAPIS_LAZULI,
            type = ItemType.MATERIAL,
            region = ItemRegion.VIBRANT_VOID,
            id = "lapis",
        )

    val CHARGED_STONE =
        item(
            name = Message.name("Charged Stone", Colour.GREY),
            material = Material.AMETHYST_SHARD,
            type = ItemType.MATERIAL,
            region = ItemRegion.VIBRANT_VOID,
            id = "charged_stone",
        )

    val PRISMARINE =
        item(
            name = Message.name("Prismarine", Colour.DARK_AQUA),
            material = Material.PRISMARINE_SHARD,
            type = ItemType.MATERIAL,
            region = ItemRegion.VIBRANT_VOID,
            id = "prismarine",
        )

    val OPAL =
        item(
            name = Message.name("Opal"),
            material = Material.BONE_MEAL,
            type = ItemType.MATERIAL,
            region = ItemRegion.VIBRANT_VOID,
            id = "opal",
        )

    val STRENGTHENED_COBBLESTONE =
        item(
            Message.name("Strengthened Cobblestone", Colour.GREY),
            Material.COBBLESTONE,
            ItemType.MATERIAL,
            ItemRegion.BAYSIDE_BEACH,
            "strengthened_cobblestone",
        ) { glowing = true }

    val STRENGTHENED_COAL =
        item(
            Message.name("Strengthened Coal", Colour.DARK_GREY),
            Material.COAL,
            ItemType.MATERIAL,
            ItemRegion.BAYSIDE_BEACH,
            "strengthened_coal",
        ) { glowing = true }

    val STRENGTHENED_COPPER =
        item(
            Message.name("Strengthened Copper", Colour.GOLD),
            Material.COPPER_INGOT,
            ItemType.MATERIAL,
            ItemRegion.BAYSIDE_BEACH,
            "strengthened_copper",
        ) { glowing = true }

    val STRENGTHENED_IRON =
        item(
            Message.name("Strengthened Iron"),
            Material.IRON_INGOT,
            ItemType.MATERIAL,
            ItemRegion.BAYSIDE_BEACH,
            "strengthened_iron",
        ) { glowing = true }

    val STRENGTHENED_GOLD =
        item(
            Message.name("Strengthened Gold", Colour.YELLOW),
            Material.GOLD_INGOT,
            ItemType.MATERIAL,
            ItemRegion.BAYSIDE_BEACH,
            "strengthened_gold",
        ) { glowing = true }

    val STRENGTHENED_DIAMOND =
        item(
            Message.name("Strengthened Diamond", Colour.AQUA),
            Material.DIAMOND,
            ItemType.MATERIAL,
            ItemRegion.BAYSIDE_BEACH,
            "strengthened_diamond",
        ) { glowing = true }

    val STRENGTHENED_QUARTZ =
        item(
            Message.name("Strengthened Quartz"),
            Material.QUARTZ,
            ItemType.MATERIAL,
            ItemRegion.CRIMSON_COVE,
            "strengthened_quartz",
        ) { glowing = true }

    val STRENGTHENED_OBSIDIAN =
        item(
            Message.name("Strengthened Obsidian", Colour.DARK_GREY),
            Material.OBSIDIAN,
            ItemType.MATERIAL,
            ItemRegion.CRIMSON_COVE,
            "strengthened_obsidian",
        ) { glowing = true }

    val STRENGTHENED_NETHERITE_SCRAP =
        item(
            Message.name("Strengthened Netherite Scrap", Colour.GREY),
            Material.NETHERITE_SCRAP,
            ItemType.MATERIAL,
            ItemRegion.CRIMSON_COVE,
            "strengthened_netherite_scrap",
        ) { glowing = true }

    val STRENGTHENED_REDSTONE =
        item(
            Message.name("Strengthened Redstone", Colour.DARK_RED),
            Material.REDSTONE,
            ItemType.MATERIAL,
            ItemRegion.CRIMSON_COVE,
            "strengthened_redstone",
        ) { glowing = true }

    val STRENGTHENED_MAGMA =
        item(
            Message.name("Strengthened Magma", Colour.GOLD),
            Material.MAGMA_CREAM,
            ItemType.MATERIAL,
            ItemRegion.CRIMSON_COVE,
            "strengthened_magma",
        ) { glowing = true }

    val STRENGTHENED_NETHERITE =
        item(
            Message.name("Strengthened Netherite", Colour.GREY),
            Material.NETHERITE_INGOT,
            ItemType.MATERIAL,
            ItemRegion.CRIMSON_COVE,
            "strengthened_netherite",
        ) { glowing = true }

    val STRENGTHENED_RUBY =
        item(
            Message.name("Strengthened Ruby", Colour.RED),
            Material.EMERALD,
            ItemType.MATERIAL,
            ItemRegion.CRIMSON_COVE,
            "strengthened_ruby",
            "ruby",
        ) { glowing = true }

    val STRENGTHENED_SAPPHIRE =
        item(
            Message.name("Strengthened Sapphire", Colour.BLUE),
            Material.EMERALD,
            ItemType.MATERIAL,
            ItemRegion.CRIMSON_COVE,
            "strengthened_sapphire",
            "sapphire",
        ) { glowing = true }

    val STRENGTHENED_PURPUR =
        item(
            name = Message.name("Strengthened Purpur", Colour.LIGHT_PURPLE),
            material = Material.POPPED_CHORUS_FRUIT,
            type = ItemType.MATERIAL,
            region = ItemRegion.VIBRANT_VOID,
            id = "strengthened_purpur",
        ) { glowing = true }

    val STRENGTHENED_LAPIS =
        item(
            name = Message.name("Strengthened Lapis", Colour.BLUE),
            material = Material.LAPIS_LAZULI,
            type = ItemType.MATERIAL,
            region = ItemRegion.VIBRANT_VOID,
            id = "strengthened_lapis",
        ) { glowing = true }

    val STRENGTHENED_CHARGED_STONE =
        item(
            name = Message.name("Strengthened Charged Stone", Colour.GREY),
            material = Material.AMETHYST_SHARD,
            type = ItemType.MATERIAL,
            region = ItemRegion.VIBRANT_VOID,
            id = "strengthened_charged_stone",
        ) { glowing = true }

    val STRENGTHENED_PRISMARINE =
        item(
            name = Message.name("Strengthened Prismarine", Colour.DARK_AQUA),
            material = Material.PRISMARINE_SHARD,
            type = ItemType.MATERIAL,
            region = ItemRegion.VIBRANT_VOID,
            id = "strengthened_prismarine",
        ) { glowing = true }

    val STRENGTHENED_OPAL =
        item(
            name = Message.name("Strengthened Opal"),
            material = Material.BONE_MEAL,
            type = ItemType.MATERIAL,
            region = ItemRegion.VIBRANT_VOID,
            id = "strengthened_opal",
        ) { glowing = true }

    val COMPACT_COBBLESTONE =
        item(
            Message.name("Compact Cobblestone", Colour.GREY),
            Material.STONE,
            ItemType.MATERIAL,
            ItemRegion.BAYSIDE_BEACH,
            "compact_cobblestone",
        ) { glowing = true }

    val COMPACT_COAL =
        item(
            Message.name("Compact Coal", Colour.DARK_GREY),
            Material.COAL_BLOCK,
            ItemType.MATERIAL,
            ItemRegion.BAYSIDE_BEACH,
            "compact_coal",
        ) { glowing = true }

    val COMPACT_COPPER =
        item(
            Message.name("Compact Copper", Colour.GOLD),
            Material.COPPER_BLOCK,
            ItemType.MATERIAL,
            ItemRegion.BAYSIDE_BEACH,
            "compact_copper",
        ) { glowing = true }

    val COMPACT_IRON =
        item(
            Message.name("Compact Iron"),
            Material.IRON_BLOCK,
            ItemType.MATERIAL,
            ItemRegion.BAYSIDE_BEACH,
            "compact_iron",
        ) { glowing = true }

    val COMPACT_GOLD =
        item(
            Message.name("Compact Gold", Colour.YELLOW),
            Material.GOLD_BLOCK,
            ItemType.MATERIAL,
            ItemRegion.BAYSIDE_BEACH,
            "compact_gold",
        ) { glowing = true }

    val COMPACT_DIAMOND =
        item(
            Message.name("Compact Diamond", Colour.AQUA),
            Material.DIAMOND_BLOCK,
            ItemType.MATERIAL,
            ItemRegion.BAYSIDE_BEACH,
            "compact_diamond",
        ) { glowing = true }

    val COMPACT_QUARTZ =
        item(
            Message.name("Compact Quartz"),
            Material.QUARTZ_BLOCK,
            ItemType.MATERIAL,
            ItemRegion.CRIMSON_COVE,
            "compact_quartz",
        ) { glowing = true }

    val COMPACT_OBSIDIAN =
        item(
            Message.name("Compact Obsidian", Colour.DARK_GREY),
            Material.CRYING_OBSIDIAN,
            ItemType.MATERIAL,
            ItemRegion.CRIMSON_COVE,
            "compact_obsidian",
        ) { glowing = true }

    val COMPACT_NETHERITE_SCRAP =
        item(
            Message.name("Compact Netherite Scrap", Colour.GREY),
            Material.ANCIENT_DEBRIS,
            ItemType.MATERIAL,
            ItemRegion.CRIMSON_COVE,
            "compact_netherite_scrap",
        ) { glowing = true }

    val COMPACT_REDSTONE =
        item(
            Message.name("Compact Redstone", Colour.DARK_RED),
            Material.REDSTONE_BLOCK,
            ItemType.MATERIAL,
            ItemRegion.CRIMSON_COVE,
            "compact_redstone",
        ) { glowing = true }

    val COMPACT_MAGMA =
        item(
            Message.name("Compact Magma", Colour.GOLD),
            Material.MAGMA_BLOCK,
            ItemType.MATERIAL,
            ItemRegion.CRIMSON_COVE,
            "compact_magma",
        ) { glowing = true }

    val COMPACT_NETHERITE =
        item(
            Message.name("Compact Netherite", Colour.GREY),
            Material.NETHERITE_BLOCK,
            ItemType.MATERIAL,
            ItemRegion.CRIMSON_COVE,
            "compact_netherite",
        ) { glowing = true }

    val COMPACT_PURPUR =
        item(
            name = Message.name("Compact Purpur", Colour.LIGHT_PURPLE),
            material = Material.POPPED_CHORUS_FRUIT,
            type = ItemType.MATERIAL,
            region = ItemRegion.VIBRANT_VOID,
            id = "compact_purpur",
        ) { glowing = true }

    val COMPACT_LAPIS =
        item(
            name = Message.name("Compact Lapis", Colour.BLUE),
            material = Material.LAPIS_LAZULI,
            type = ItemType.MATERIAL,
            region = ItemRegion.VIBRANT_VOID,
            id = "compact_lapis",
        ) { glowing = true }

    val COMPACT_CHARGED_STONE =
        item(
            Message.name("Compact Charged Stone", Colour.GREY),
            Material.AMETHYST_SHARD,
            ItemType.MATERIAL,
            ItemRegion.VIBRANT_VOID,
            "compact_charged_stone",
        ) { glowing = true }

    val COMPACT_PRISMARINE =
        item(
            Message.name("Compact Prismarine", Colour.DARK_AQUA),
            Material.PRISMARINE_SHARD,
            ItemType.MATERIAL,
            ItemRegion.VIBRANT_VOID,
            "compact_prismarine",
        ) { glowing = true }

    val COMPACT_OPAL =
        item(
            Message.name("Compact Opal"),
            Material.BONE_MEAL,
            ItemType.MATERIAL,
            ItemRegion.VIBRANT_VOID,
            "compact_opal",
        ) { glowing = true }
}
