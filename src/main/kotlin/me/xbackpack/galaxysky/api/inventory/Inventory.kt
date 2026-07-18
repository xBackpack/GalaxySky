package me.xbackpack.galaxysky.api.inventory

import me.xbackpack.galaxysky.api.item.Item
import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.api.util.getId
import me.xbackpack.galaxysky.api.util.vanillaItem
import me.xbackpack.galaxysky.enum.customItems.GalaxySkyItem
import me.xbackpack.galaxysky.service.ListenerService
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.InventoryHolder

interface Inventory : InventoryHolder {
    val title: Message
    val rows: Int
    val fillGrey: Boolean
    val cancelClicks: Boolean
    val cancelMisc: Boolean
    val locationInWorld: Location?

    val usefulSlots: MutableMap<Int, Item>
    val slotHandlers: MutableMap<Int, (InventoryClickEvent) -> Unit>
    val inv: org.bukkit.inventory.Inventory

    fun reserveSlots(vararg slots: Pair<Int, Item>) = usefulSlots.putAll(slots)

    fun getItemFromSlot(idx: Int): Item? = GalaxySkyItem.valueOf(inv.getItem(idx)?.getId() ?: return null).item

    fun setSlot(
        idx: Int,
        item: Item,
    ) = inv.setItem(idx, item.build())

    fun onClick(
        vararg slots: Int,
        block: (InventoryClickEvent) -> Unit,
    ) {
        ListenerService.hookEvent<InventoryClickEvent> { event ->
            if (event.clickedInventory?.holder == this) {
                if (event.slot in slots) {
                    block(event)
                } else if (cancelClicks) {
                    event.isCancelled = true
                }
            }
        }
    }

    fun onClose(block: (InventoryCloseEvent) -> Unit) {
        ListenerService.hookEvent<InventoryCloseEvent> {
            if (it.inventory.holder == this) {
                block(it)
            }
        }
    }

    companion object {
        val GREY = vanillaItem(Message.empty(), Material.GRAY_STAINED_GLASS_PANE)
    }
}
