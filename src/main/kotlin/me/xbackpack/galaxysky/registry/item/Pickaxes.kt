package me.xbackpack.galaxysky.registry.item

import me.xbackpack.galaxysky.api.item.Item
import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.enum.item.ItemRegion
import me.xbackpack.galaxysky.enum.item.ItemStatType
import me.xbackpack.galaxysky.enum.item.ItemType
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material

object Pickaxes {
    object BaysideBeach {
        val STONE_PICKAXE_1 =
            Item.create(
                Message.create {
                    text("Stone Pickaxe") {
                        colour(NamedTextColor.GRAY)
                    }

                    space()

                    text("1") {
                        colour(NamedTextColor.AQUA)
                    }
                },
                Material.STONE_PICKAXE,
                ItemType.PICKAXE,
                ItemRegion.BAYSIDE_BEACH,
                "stone_pickaxe_1",
            ) {
                unbreakable = true

                stats[ItemStatType.BREAKING_POWER] = 1
                stats[ItemStatType.MINING_SPEED] = 400
                stats[ItemStatType.ORE_FORTUNE] = 0
            }
    }

    val ADMIN_PICKAXE =
        Item.create(
            Message.name("Admin Pickaxe", NamedTextColor.RED, true),
            Material.NETHERITE_PICKAXE,
            ItemType.PICKAXE,
            ItemRegion.CRIMSON_COVE,
            "admin_pickaxe",
        ) {
            description +=
                Message.create {
                    text("Admin Only Item!") {
                        colour(NamedTextColor.RED)
                        underlined()
                    }
                }
            description +=
                Message.create {
                    text("Failure to return this to a member of") {
                        colour(NamedTextColor.DARK_RED)
                    }
                }
            description +=
                Message.create {
                    text("staff will result in a permanent ban.") {
                        colour(NamedTextColor.DARK_RED)
                    }
                }

            unbreakable = true

            stats[ItemStatType.BREAKING_POWER] = 100
            stats[ItemStatType.MINING_SPEED] = 250000
            stats[ItemStatType.ORE_FORTUNE] = 900
        }
}
