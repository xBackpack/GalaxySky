package me.xbackpack.galaxysky.enum.block

import com.sk89q.worldguard.protection.regions.ProtectedRegion
import me.xbackpack.galaxysky.service.LocationService
import me.xbackpack.galaxysky.service.WorldGuardService.fill
import me.xbackpack.galaxysky.service.WorldGuardService.getRegion
import me.xbackpack.galaxysky.service.WorldGuardService.isInsideRegion
import org.bukkit.Bukkit
import org.bukkit.World

enum class MineType(
    private val world: World,
    private val blockType: BlockType,
    private val id: String,
    private val double: Boolean,
) {
    STARTER(LocationService.WORLD, BlockType.STONE, "starter", false),
    STONE(LocationService.WORLD, BlockType.STONE, "stone", false),
    COAL(LocationService.WORLD, BlockType.COAL, "coal", false),
    IRON(LocationService.WORLD, BlockType.IRON, "iron", true),
    COPPER(LocationService.WORLD, BlockType.COPPER, "copper", true),
    GOLD(LocationService.WORLD, BlockType.GOLD, "gold", false),
    DIAMOND(LocationService.WORLD, BlockType.DIAMOND, "diamond", true),
    QUARTZ(LocationService.NETHER, BlockType.QUARTZ, "quartz", false),
    OBSIDIAN(LocationService.NETHER, BlockType.OBSIDIAN, "obsidian", false),
    NETHERITE(LocationService.NETHER, BlockType.NETHERITE, "netherite", true),
    MAGMA(LocationService.NETHER, BlockType.MAGMA, "magma", true),
    REDSTONE(LocationService.NETHER, BlockType.REDSTONE, "redstone", true),
    PURPUR(LocationService.END, BlockType.PURPUR, "purpur", true),
    LAPIS(LocationService.END, BlockType.LAPIS, "lapis", true),
    CHARGED_STONE(LocationService.END, BlockType.CHARGED_STONE, "chargedstone", true),
    PRISMARINE(LocationService.END, BlockType.PRISMARINE, "prismarine", true),
    OPAL(LocationService.END, BlockType.OPAL, "opal", true),
    ;

    fun fill() {
        val regions = mutableListOf<ProtectedRegion>()

        double
            .takeIf { it }
            ?.let {
                regions.add(world.getRegion("${id}1") ?: error("No region called ${id}1"))
                regions.add(world.getRegion("${id}2") ?: error("No region called ${id}2"))
            } ?: regions.add(world.getRegion(id) ?: error("No region called $id"))

        regions.forEach { region ->
            region.fill(world, blockType)

            Bukkit.getOnlinePlayers().forEach { player ->
                if (region.isInsideRegion(player)) {
                    player.teleport(player.location.apply { y = region.maximumPoint.y() + 1.0 })
                }
            }
        }
    }
}
