package me.xbackpack.galaxysky.registry.inventory

import me.xbackpack.galaxysky.Triplet
import me.xbackpack.galaxysky.api.item.Item
import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.api.util.giveItems
import me.xbackpack.galaxysky.api.util.inventory
import me.xbackpack.galaxysky.api.util.msg
import me.xbackpack.galaxysky.api.util.vanillaItem
import me.xbackpack.galaxysky.enum.Colour
import me.xbackpack.galaxysky.registry.item.Materials
import me.xbackpack.galaxysky.service.LocationService
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryAction

object Forge {
    private val ORANGE = vanillaItem(Message.name("Fuel Slot", Colour.GOLD), Material.ORANGE_STAINED_GLASS_PANE)
    private val RED = vanillaItem(Message.name("Item Slot", Colour.RED), Material.RED_STAINED_GLASS_PANE)
    private val ANVIL = vanillaItem(Message.name("Click to Forge!", Colour.DARK_GREY), Material.ANVIL)
    private val ANVIL_ALL = vanillaItem(Message.name("Forge All!", Colour.DARK_GREY), Material.ANVIL)
    private val AIR = Item.AIR

    val FORGE =
        inventory(
            title = Message.name("Forge", Colour.RED),
            rows = 6,
            fillGrey = true,
            cancelClicks = true,
            cancelMisc = true,
            location = LocationService.FORGE,
        ) {
            reserveSlots(
                4 to ORANGE,
                10 to AIR,
                12 to ORANGE,
                13 to AIR,
                14 to ORANGE,
                16 to AIR,
                19 to RED,
                22 to ORANGE,
                25 to RED,
                28 to RED,
                31 to ANVIL,
                34 to RED,
                37 to RED,
                38 to RED,
                39 to RED,
                40 to AIR,
                41 to RED,
                42 to RED,
                43 to RED,
                49 to ANVIL_ALL,
            )

            fun getLeft() = getItemFromSlot(10)

            fun getFuel() = getItemFromSlot(13)

            fun getRight() = getItemFromSlot(16)

            fun getResult() = getItemFromSlot(40)

            fun validateFuel() = getFuel()?.id == Materials.STRENGTHENED_MAGMA.id

            fun validateRecipe() =
                RECIPES
                    .firstOrNull {
                        (it.first.id == getLeft()?.id && it.second.id == getRight()?.id) ||
                            (it.first.id == getRight()?.id && it.second.id == getLeft()?.id)
                    }?.third

            fun forge(all: Boolean): Message? {
                val forgedItem =
                    validateRecipe() ?: run {
                        return Message.name("Claim the current forged items first!", Colour.RED)
                    }

                getResult()
                    ?.takeIf { it.id != forgedItem.id }
                    ?.let {
                        return Message.name("Claim the current forged items first!", Colour.RED)
                    }

                val (left, fuel, right) =
                    listOfNotNull(getLeft(), getFuel(), getRight()).also {
                        if (it.size < 3) error("Missing required items")
                    }

                val total = if (all) minOf(left.amount, fuel.amount, right.amount) else 1

                left.amount -= total
                fuel.amount -= total
                right.amount -= total

                getResult()
                    ?.apply { amount += total }
                    ?: setSlot(40, forgedItem.apply { amount = total })

                return null
            }

            fun getOverflow() = listOfNotNull(getLeft(), getFuel(), getRight())

            onClick { event ->
                val player = event.whoClicked

                if (event.action in setOf(InventoryAction.MOVE_TO_OTHER_INVENTORY, InventoryAction.NOTHING)) {
                    event.isCancelled = true
                    return@onClick
                }

                event.isCancelled =
                    when (event.rawSlot) {
                        10, 16 -> false
                        13 -> !validateFuel()
                        31 -> true.also { forge(false)?.let { player.msg(it) } }
                        40 -> getResult() == null
                        49 -> true.also { forge(true)?.let { player.msg(it) } }
                        else -> true
                    }
            }

            onClose { event ->
                val player = event.player as Player

                player.giveItems(getOverflow())
            }

            TODO("MAKE SURE FORGE IS FINISHED, MAKE COMMAND, MAKE PAGED INVENTORIES, MAKE CATALOGUE, AND TEST EVERYTHING!!!!!")
        }

    private val RECIPES =
        listOf(
            Triplet(
                Materials.STRENGTHENED_NETHERITE_SCRAP,
                Materials.STRENGTHENED_GOLD,
                Materials.NETHERITE,
            ),
            Triplet(
                Materials.STRENGTHENED_REDSTONE,
                Materials.STRENGTHENED_DIAMOND,
                Materials.RUBY,
            ),
            Triplet(
                Materials.STRENGTHENED_RUBY,
                Materials.STRENGTHENED_LAPIS,
                Materials.SAPPHIRE,
            ),
        )
}
