package me.xbackpack.galaxysky.service

import com.sk89q.worldguard.protection.flags.Flags
import me.xbackpack.galaxysky.Triplet
import me.xbackpack.galaxysky.api.item.CustomItem
import me.xbackpack.galaxysky.api.util.getStat
import me.xbackpack.galaxysky.api.util.giveItem
import me.xbackpack.galaxysky.api.util.incStat
import me.xbackpack.galaxysky.api.util.msg
import me.xbackpack.galaxysky.enum.Colour
import me.xbackpack.galaxysky.enum.block.MineType
import me.xbackpack.galaxysky.enum.item.ItemStatType
import me.xbackpack.galaxysky.enum.player.PlayerStatType
import me.xbackpack.galaxysky.registry.item.Materials
import me.xbackpack.galaxysky.service.LuckPermsService.givePermission
import me.xbackpack.galaxysky.service.WorldGuardService.getFlag
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

object MiningService {
    fun resetMines() = MineType.entries.forEach(MineType::fill)

    fun onBlockBreak(event: BlockBreakEvent) {
        val player = event.player
        val block = event.block

        if (player.gameMode != GameMode.SURVIVAL) return

        if (!block.location.getFlag(player, Flags.BLOCK_BREAK)) return

        val tool = player.inventory.itemInMainHand

        val (drop, blockAmount, hardness) = getDataFromBlock(block)

        val breakingPower = tool.getStat(ItemStatType.BREAKING_POWER)

        if (breakingPower < hardness) {
            player.msg {
                text("Breaking power too low to break this block!") {
                    colour(Colour.RED)
                    bold()
                }
            }

            event.isCancelled = true
            return
        }

        event.isDropItems = false
        event.expToDrop = 0

        val newAmount = if (blockAmount > 0) blockAmount else getAmountFromItem(tool)

        drop?.let { item ->
            item.amount = newAmount
            player.giveItem(item)
        }

        player.incStat(PlayerStatType.BLOCKS_MINED)

        when (player.getStat(PlayerStatType.BLOCKS_MINED)) {
            10000 -> {
                player.givePermission("galaxysky.world.nether")
                player.msg {
                    section {
                        text("You have now unlocked the nether! (")

                        note("/world nether")

                        text(")")

                        colour(Colour.GREEN)
                    }
                }
                player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 0.75f)
            }

            25000 -> {
                player.givePermission("galaxysky.world.end")
                player.msg {
                    section {
                        text("You have now unlocked the end! (")

                        note("/world end")

                        text(")")

                        colour(Colour.GREEN)
                    }
                }
                player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 0.75f)
            }
        }
    }

    private fun getDataFromBlock(block: Block): Triplet<CustomItem?, Int, Int> =
        when (block.type) {
            // Overworld
            Material.STONE -> Triplet(Materials.COBBLESTONE, 0, 0)

            Material.COAL_ORE -> Triplet(Materials.COAL, 0, 1)

            Material.COAL_BLOCK -> Triplet(Materials.COAL, 9, 1)

            Material.IRON_ORE -> Triplet(Materials.IRON, 0, 2)

            Material.IRON_BLOCK -> Triplet(Materials.IRON, 9, 2)

            Material.COPPER_ORE -> Triplet(Materials.COPPER, 0, 3)

            Material.WAXED_COPPER_BLOCK -> Triplet(Materials.COPPER, 9, 3)

            Material.GOLD_ORE -> Triplet(Materials.GOLD, 0, 3)

            Material.GOLD_BLOCK -> Triplet(Materials.GOLD, 9, 3)

            Material.DIAMOND_ORE -> Triplet(Materials.DIAMOND, 0, 4)

            Material.DIAMOND_BLOCK -> Triplet(Materials.DIAMOND, 9, 4)

            // Nether
            Material.NETHER_QUARTZ_ORE -> Triplet(Materials.QUARTZ, 0, 5)

            Material.QUARTZ_BLOCK -> Triplet(Materials.QUARTZ, 6, 5)

            Material.OBSIDIAN -> Triplet(Materials.OBSIDIAN, 0, 6)

            Material.CRYING_OBSIDIAN -> Triplet(Materials.OBSIDIAN, 6, 6)

            Material.ANCIENT_DEBRIS -> Triplet(Materials.NETHERITE_SCRAP, 0, 7)

            Material.NETHERITE_BLOCK -> Triplet(Materials.NETHERITE_SCRAP, 6, 7)

            Material.DEEPSLATE_REDSTONE_ORE -> Triplet(Materials.REDSTONE, 0, 8)

            Material.REDSTONE_BLOCK -> Triplet(Materials.REDSTONE, 6, 8)

            Material.MAGMA_BLOCK -> Triplet(Materials.MAGMA, 0, 9)

            // End
            Material.PURPUR_BLOCK -> Triplet(Materials.PURPUR, 0, 10)

            Material.PINK_GLAZED_TERRACOTTA -> Triplet(Materials.PURPUR, 4, 10)

            Material.LAPIS_ORE -> Triplet(Materials.LAPIS, 0, 11)

            Material.LAPIS_BLOCK -> Triplet(Materials.LAPIS, 4, 11)

            Material.RESPAWN_ANCHOR -> Triplet(Materials.CHARGED_STONE, 0, 12)

            Material.PRISMARINE -> Triplet(Materials.PRISMARINE, 0, 13)

            Material.DARK_PRISMARINE -> Triplet(Materials.PRISMARINE, 4, 13)

            Material.BLACK_STAINED_GLASS -> Triplet(Materials.OPAL, 0, 14)

            else -> Triplet(null, 6, 0)
        }

    private fun getAmountFromItem(item: ItemStack): Int {
        val fortuneLevel = item.getStat(ItemStatType.ORE_FORTUNE)

        var guaranteedDrops = 1

        guaranteedDrops += (fortuneLevel / 100)

        val remainder = fortuneLevel % 100

        if (Random.nextDouble() <= (remainder.toDouble() / 100)) guaranteedDrops++

        return guaranteedDrops
    }
}
