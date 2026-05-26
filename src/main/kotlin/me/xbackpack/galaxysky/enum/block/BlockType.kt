package me.xbackpack.galaxysky.enum.block

import com.sk89q.worldedit.world.block.BlockTypes

enum class BlockType(
    val mainType: com.sk89q.worldedit.world.block.BlockType?,
    val rarerType: com.sk89q.worldedit.world.block.BlockType? = mainType,
) {
    STONE(BlockTypes.STONE),
    COAL(BlockTypes.COAL_ORE, BlockTypes.COAL_BLOCK),
    IRON(BlockTypes.IRON_ORE, BlockTypes.IRON_BLOCK),
    COPPER(BlockTypes.COPPER_ORE, BlockTypes.WAXED_COPPER_BLOCK),
    GOLD(BlockTypes.GOLD_ORE, BlockTypes.GOLD_BLOCK),
    DIAMOND(BlockTypes.DIAMOND_ORE, BlockTypes.DIAMOND_BLOCK),
    QUARTZ(BlockTypes.NETHER_QUARTZ_ORE, BlockTypes.QUARTZ_BLOCK),
    OBSIDIAN(BlockTypes.OBSIDIAN, BlockTypes.CRYING_OBSIDIAN),
    NETHERITE(BlockTypes.ANCIENT_DEBRIS, BlockTypes.NETHERITE_BLOCK),
    MAGMA(BlockTypes.MAGMA_BLOCK),
    REDSTONE(BlockTypes.REDSTONE_ORE, BlockTypes.REDSTONE_BLOCK),
    PURPUR(BlockTypes.PURPUR_BLOCK, BlockTypes.PINK_GLAZED_TERRACOTTA),
    LAPIS(BlockTypes.LAPIS_ORE, BlockTypes.LAPIS_BLOCK),
    CHARGED_STONE(BlockTypes.RESPAWN_ANCHOR),
    PRISMARINE(BlockTypes.PRISMARINE, BlockTypes.DARK_PRISMARINE),
    OPAL(BlockTypes.BLACK_STAINED_GLASS),
}
