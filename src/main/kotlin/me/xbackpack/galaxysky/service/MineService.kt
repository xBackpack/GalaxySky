package me.xbackpack.galaxysky.service

import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldedit.world.block.BlockType
import com.sk89q.worldedit.world.block.BlockTypes
import me.xbackpack.galaxysky.GalaxySky
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import kotlin.random.Random

object MineService {
    private val mineFile = GalaxySky.getFile("", "mines.yml")

    private val mines =
        setOf<Mine>(
            getMine("starter"),
            getMine("stone"),
            getMine("coal"),
            getMine("iron1"),
            getMine("iron2"),
            getMine("copper1"),
            getMine("copper2"),
            getMine("gold"),
            getMine("diamond1"),
            getMine("diamond2"),
            getMine("quartz"),
            getMine("obsidian"),
            getMine("netherite1"),
            getMine("netherite2"),
            getMine("magma1"),
            getMine("magma2"),
            getMine("redstone1"),
            getMine("redstone2"),
            getMine("purpur1"),
            getMine("purpur2"),
            getMine("lapis1"),
            getMine("lapis2"),
            getMine("chargedstone1"),
            getMine("chargedstone2"),
            getMine("prismarine1"),
            getMine("prismarine2"),
            getMine("opal1"),
            getMine("opal2"),
        )

    fun resetMines() = mines.forEach(Mine::fill)

    private fun getMine(idLower: String): Mine {
        val config = YamlConfiguration.loadConfiguration(mineFile)

        config.getKeys(false).forEach { key ->
            val region = config.getConfigurationSection(key) ?: error("Key $key is not a configuration section")

            region
                .getConfigurationSection(idLower)
                ?.let { mineConfig ->
                    val world = LocationService[region.name] ?: error("World ${region.name} does not exist")

                    val blockTypeString = mineConfig.getString("block_type") ?: error("Mine $idLower has no block type")
                    val rareBlockTypeString = mineConfig.getString("rare_block_type") ?: blockTypeString

                    val region = WorldGuardService.getRegion(world, idLower) ?: error("Mine $idLower has no WorldGuard region")

                    val blockType = BlockTypes.get(blockTypeString.lowercase())
                    val rareBlockType = BlockTypes.get(rareBlockTypeString.lowercase())

                    return Mine(idLower, world, region.minimumPoint, region.maximumPoint, blockType, rareBlockType)
                }
        }

        error("No mine of name $idLower")
    }

    private class Mine(
        val id: String,
        private val world: World,
        private val min: BlockVector3,
        private val max: BlockVector3,
        private val common: BlockType?,
        private val rare: BlockType?,
    ) {
        fun fill() {
            val newWorld = BukkitAdapter.adapt(world)
            val worldEdit = WorldEdit.getInstance()
            val editSession = worldEdit.newEditSession(newWorld)

            Bukkit.getOnlinePlayers().forEach {
                it
                    .takeIf(::isInsideRegion)
                    ?.teleport(it.location.apply { y = max.y() + 1.0 })
            }

            editSession.use {
                for (x in min.x()..max.x()) {
                    for (y in min.y()..max.y()) {
                        for (z in min.z()..max.z()) {
                            val blockType = if (Random.nextDouble() < 0.9) common?.defaultState else rare?.defaultState
                            editSession.setBlock(BlockVector3.at(x, y, z), blockType)
                        }
                    }
                }
            }
        }

        fun isInsideRegion(player: Player) =
            with(player.location) {
                x in min.x().toDouble()..max.x().toDouble() &&
                    y in min.y().toDouble()..max.y().toDouble() &&
                    z in min.z().toDouble()..max.z().toDouble()
            }
    }
}
