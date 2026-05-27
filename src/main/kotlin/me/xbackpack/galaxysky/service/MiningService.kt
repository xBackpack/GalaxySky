package me.xbackpack.galaxysky.service

import com.sk89q.worldguard.protection.flags.Flags
import me.xbackpack.galaxysky.api.Triplet
import me.xbackpack.galaxysky.api.item.Item
import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.enum.block.MineType
import me.xbackpack.galaxysky.enum.item.ItemStatType
import me.xbackpack.galaxysky.enum.player.PlayerStatType
import me.xbackpack.galaxysky.giveItem
import me.xbackpack.galaxysky.registry.item.Materials.BaysideBeach
import me.xbackpack.galaxysky.registry.item.Materials.CrimsonCove
import me.xbackpack.galaxysky.registry.item.Materials.VibrantVoid
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

        if (!WorldGuardService.getFlag(player, block.location, Flags.BLOCK_BREAK)) return

        val tool = player.inventory.itemInMainHand

        val (drop, blockAmount, hardness) = getDataFromBlock(block)

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

        val newAmount = if (blockAmount > 0) blockAmount else getAmountFromItem(tool)

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
                player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 0.75f)
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
                player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 0.75f)
            }
        }
    }

    private fun getDataFromBlock(block: Block): Triplet<Item?, Int, Int> =
        when (block.type) {
            // Overworld
            Material.STONE -> Triplet(BaysideBeach.COBBLESTONE, 0, 0)

            Material.COAL_ORE -> Triplet(BaysideBeach.COAL, 0, 1)

            Material.COAL_BLOCK -> Triplet(BaysideBeach.COAL, 9, 1)

            Material.IRON_ORE -> Triplet(BaysideBeach.IRON, 0, 2)

            Material.IRON_BLOCK -> Triplet(BaysideBeach.IRON, 9, 2)

            Material.COPPER_ORE -> Triplet(BaysideBeach.COPPER, 0, 3)

            Material.WAXED_COPPER_BLOCK -> Triplet(BaysideBeach.COPPER, 9, 3)

            Material.GOLD_ORE -> Triplet(BaysideBeach.GOLD, 0, 3)

            Material.GOLD_BLOCK -> Triplet(BaysideBeach.GOLD, 9, 3)

            Material.DIAMOND_ORE -> Triplet(BaysideBeach.DIAMOND, 0, 4)

            Material.DIAMOND_BLOCK -> Triplet(BaysideBeach.DIAMOND, 9, 4)

            // Nether
            Material.NETHER_QUARTZ_ORE -> Triplet(CrimsonCove.QUARTZ, 0, 5)

            Material.QUARTZ_BLOCK -> Triplet(CrimsonCove.QUARTZ, 6, 5)

            Material.OBSIDIAN -> Triplet(CrimsonCove.OBSIDIAN, 0, 6)

            Material.CRYING_OBSIDIAN -> Triplet(CrimsonCove.OBSIDIAN, 6, 6)

            Material.ANCIENT_DEBRIS -> Triplet(CrimsonCove.NETHERITE_SCRAP, 0, 7)

            Material.NETHERITE_BLOCK -> Triplet(CrimsonCove.NETHERITE_SCRAP, 6, 7)

            Material.DEEPSLATE_REDSTONE_ORE -> Triplet(CrimsonCove.REDSTONE, 0, 8)

            Material.REDSTONE_BLOCK -> Triplet(CrimsonCove.REDSTONE, 6, 8)

            Material.MAGMA_BLOCK -> Triplet(CrimsonCove.MAGMA, 0, 9)

            // End
            Material.PURPUR_BLOCK -> Triplet(VibrantVoid.PURPUR, 0, 10)

            Material.PINK_GLAZED_TERRACOTTA -> Triplet(VibrantVoid.PURPUR, 4, 10)

            Material.LAPIS_ORE -> Triplet(VibrantVoid.LAPIS, 0, 11)

            Material.LAPIS_BLOCK -> Triplet(VibrantVoid.LAPIS, 4, 11)

            Material.RESPAWN_ANCHOR -> Triplet(VibrantVoid.CHARGED_STONE, 0, 12)

            Material.PRISMARINE -> Triplet(VibrantVoid.PRISMARINE, 0, 13)

            Material.DARK_PRISMARINE -> Triplet(VibrantVoid.PRISMARINE, 4, 13)

            Material.BLACK_STAINED_GLASS -> Triplet(VibrantVoid.OPAL, 0, 14)

            else -> Triplet(null, 6, 0)
        }

    fun getAmountFromItem(item: ItemStack): Int {
        val fortuneLevel = ItemData.Stats[item]?.get(ItemStatType.ORE_FORTUNE) ?: 0

        var guaranteedDrops = 1

        guaranteedDrops += (fortuneLevel / 100)

        val remainder = fortuneLevel % 100

        if (Random.nextDouble() < (remainder.toDouble() / 100)) guaranteedDrops++

        return guaranteedDrops
    }
}
