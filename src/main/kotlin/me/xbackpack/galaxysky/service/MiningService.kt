package me.xbackpack.galaxysky.service

import com.sk89q.worldguard.protection.flags.Flags
import me.xbackpack.galaxysky.GalaxySky
import me.xbackpack.galaxysky.api.Triplet
import me.xbackpack.galaxysky.api.item.Item
import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.enum.block.MineType
import me.xbackpack.galaxysky.enum.item.ItemStatType
import me.xbackpack.galaxysky.enum.player.PlayerStatType
import me.xbackpack.galaxysky.giveItem
import me.xbackpack.galaxysky.registry.item.Materials.BaysideBeach
import me.xbackpack.galaxysky.sendMessage
import me.xbackpack.galaxysky.service.PDCService.ItemData
import me.xbackpack.galaxysky.service.PDCService.PlayerData
import net.kyori.adventure.text.format.NamedTextColor
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
        GalaxySky.logger.info("Survival")

        if (!WorldGuardService.getFlag(player, block.location, Flags.BLOCK_BREAK)) return

        val tool = player.inventory.itemInMainHand

        val (drop, isBlock, hardness) = getDataFromBlock(block)

        val breakingPower = ItemData.Stats[tool]?.get(ItemStatType.BREAKING_POWER) ?: 0

        if (breakingPower < hardness) {
            player.sendMessage(
                Message.create {
                    text("Breaking power too low to break this block!") {
                        colour(NamedTextColor.RED)
                        bold()
                    }
                },
            )

            event.isCancelled = true
            return
        }

        event.isDropItems = false
        event.expToDrop = 0

        val newAmount = if (isBlock) 9 else getAmountFromItem(tool)

        drop?.let { item ->
            item.configure { amount = newAmount }
            player.giveItem(item)
        }

        PlayerData.Stats.inc(player, PlayerStatType.BLOCKS_MINED)

        when (PlayerData.Stats[player, PlayerStatType.BLOCKS_MINED]) {
            10000 -> {
                LuckPermsService.givePermission(player, "galaxysky.world.nether")
                player.sendMessage(
                    Message.create {
                        section {
                            text("You have now unlocked the nether! (")

                            note("/world nether")

                            text(")")

                            colour(NamedTextColor.GREEN)
                        }
                    },
                )
                player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 2f, 1f)
            }

            25000 -> {
                LuckPermsService.givePermission(player, "galaxysky.world.end")
                player.sendMessage(
                    Message.create {
                        section {
                            text("You have now unlocked the end! (")

                            note("/world end")

                            text(")")

                            colour(NamedTextColor.GREEN)
                        }
                    },
                )
                player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 2f, 1f)
            }
        }
    }

    private fun getDataFromBlock(block: Block): Triplet<Item?, Boolean, Int> =
        when (block.type) {
            Material.STONE -> Triplet(BaysideBeach.COBBLESTONE, false, 0)
            Material.COAL_ORE -> Triplet(BaysideBeach.COAL, false, 1)
            Material.COAL_BLOCK -> Triplet(BaysideBeach.COAL, true, 1)
            Material.IRON_ORE -> Triplet(BaysideBeach.IRON, false, 2)
            Material.IRON_BLOCK -> Triplet(BaysideBeach.IRON, true, 2)
            Material.COPPER_ORE -> Triplet(BaysideBeach.COPPER, false, 3)
            Material.WAXED_COPPER_BLOCK -> Triplet(BaysideBeach.COPPER, true, 3)
            Material.GOLD_ORE -> Triplet(BaysideBeach.GOLD, false, 3)
            Material.GOLD_BLOCK -> Triplet(BaysideBeach.GOLD, true, 3)
            Material.DIAMOND_ORE -> Triplet(BaysideBeach.DIAMOND, false, 4)
            Material.DIAMOND_BLOCK -> Triplet(BaysideBeach.DIAMOND, true, 4)
            else -> Triplet(null, false, 0)
        }

    fun getAmountFromItem(item: ItemStack): Int {
        val fortuneLevel = ItemData.Stats[item]?.get(ItemStatType.ORE_FORTUNE) ?: 0

        var guaranteedDrops = 1

        guaranteedDrops += (fortuneLevel / 10)

        val remainder = fortuneLevel % 10

        val extraDrop = Random.nextInt(remainder + 1)

        return guaranteedDrops + extraDrop
    }
}
