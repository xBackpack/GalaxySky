package me.xbackpack.galaxysky.commands.commandTypes

import me.xbackpack.galaxysky.util.Item
import org.bukkit.entity.Player

object InventoryCommand {
    // CheckInventory
    // CheckArmour TODO(will be parameterized with an enum class called ArmourType) <T : ArmorType>

    interface CheckHand : CommandBase {
        override val requiresPlayer: Boolean
            get() = true

        fun getIds(): Set<String>

        fun funToRun(player: Player)

        fun checkItems(player: Player) {
            val ids = getIds()
            val itemStack = player.inventory.itemInMainHand

            if (itemStack.type.isAir) return

            val item = Item(itemStack)
            val id = item.id

            if (ids.contains(id)) funToRun(player)
        }
    }
}
