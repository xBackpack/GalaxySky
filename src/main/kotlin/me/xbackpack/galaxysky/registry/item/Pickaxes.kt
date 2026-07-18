package me.xbackpack.galaxysky.registry.item

import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.api.util.item
import me.xbackpack.galaxysky.api.util.message
import me.xbackpack.galaxysky.enum.Colour
import me.xbackpack.galaxysky.enum.item.ItemRegion
import me.xbackpack.galaxysky.enum.item.ItemStatType
import me.xbackpack.galaxysky.enum.item.ItemType
import org.bukkit.Material

object Pickaxes {
    val WOODEN_PICKAXE_1 =
        item(
            name =
                message {
                    text("Stone Pickaxe") {
                        colour(Colour.GREY)
                    }

                    space()

                    text("1") {
                        colour(Colour.AQUA)
                    }
                },
            material = Material.WOODEN_PICKAXE,
            type = ItemType.PICKAXE,
            region = ItemRegion.BAYSIDE_BEACH,
            id = "stone_pickaxe_1",
        ) {
            unbreakable = true

            stats[ItemStatType.BREAKING_POWER] = 1
            stats[ItemStatType.MINING_SPEED] = 200
            stats[ItemStatType.ORE_FORTUNE] = 0
        }

    val ADMIN_PICKAXE =
        item(
            name = Message.name("Admin Pickaxe", Colour.RED, true),
            material = Material.NETHERITE_PICKAXE,
            type = ItemType.PICKAXE,
            region = ItemRegion.CRIMSON_COVE,
            id = "admin_pickaxe",
        ) {
            description +=
                message {
                    text("Admin Only Item!") {
                        colour(Colour.RED)
                        underlined()
                    }
                }
            description +=
                message {
                    text("Failure to return this to a member of") {
                        colour(Colour.DARK_RED)
                    }
                }
            description +=
                message {
                    text("staff will result in a permanent ban.") {
                        colour(Colour.DARK_RED)
                    }
                }

            unbreakable = true

            stats[ItemStatType.BREAKING_POWER] = 100
            stats[ItemStatType.MINING_SPEED] = 250000
            stats[ItemStatType.ORE_FORTUNE] = 900
        }
}
