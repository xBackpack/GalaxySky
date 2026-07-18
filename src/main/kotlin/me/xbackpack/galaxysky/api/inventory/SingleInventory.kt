package me.xbackpack.galaxysky.api.inventory

import me.xbackpack.galaxysky.GalaxySky
import me.xbackpack.galaxysky.api.item.Item
import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.api.util.showInventory
import me.xbackpack.galaxysky.service.ListenerService
import org.bukkit.Location
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.player.PlayerInteractEvent

@InventoryDsl
class SingleInventory(
    override val title: Message,
    override val rows: Int,
    override val fillGrey: Boolean,
    override val cancelClicks: Boolean,
    override val cancelMisc: Boolean,
    override val locationInWorld: Location?,
) : Inventory {
    override val usefulSlots = mutableMapOf<Int, Item>()
    override val slotHandlers = mutableMapOf<Int, (InventoryClickEvent) -> Unit>()
    override val inv = GalaxySky.createInventory(this, rows, title)

    init {
        ListenerService.hookEvent<PlayerInteractEvent> { event ->
            if (event.action != Action.RIGHT_CLICK_BLOCK && event.clickedBlock?.location == locationInWorld) {
                event.isCancelled = true
                event.player.showInventory(this)
            }
        }

        ListenerService.hookEvent<InventoryDragEvent> { event ->
            event.isCancelled = cancelMisc && event.inventory.holder == this
        }

        ListenerService.hookEvent<InventoryClickEvent> { event ->
            if (event.clickedInventory?.holder == this) {
                if (event.action == InventoryAction.NOTHING ||
                    (
                        cancelMisc && event.action in
                            arrayOf(
                                InventoryAction.MOVE_TO_OTHER_INVENTORY,
                                InventoryAction.HOTBAR_SWAP,
                                InventoryAction.COLLECT_TO_CURSOR,
                                InventoryAction.DROP_ALL_SLOT,
                                InventoryAction.DROP_ONE_SLOT,
                                InventoryAction.UNKNOWN,
                            )
                    )
                ) {
                    event.isCancelled = true
                    return@hookEvent
                }

                slotHandlers[event.slot]
                    ?.let { it(event) }
                    ?: run { event.isCancelled = cancelClicks }
            }
        }
    }

    override fun getInventory(): org.bukkit.inventory.Inventory {
        inv.contents =
            Array(rows * 9) {
                usefulSlots[it]
                    ?.build()
                    ?: if (fillGrey) Inventory.GREY.build() else Item.AIR.build()
            }

        return inv
    }
}
