package me.xbackpack.galaxysky.commands.commandTypes

import me.xbackpack.galaxysky.util.Item
import org.bukkit.inventory.Inventory

object InventoryCommand {
    interface Create : CommandBase {
        override val requiresPlayer
            get() = true

        fun getInventory(player: org.bukkit.entity.Player): Inventory

        fun openInventory(player: org.bukkit.entity.Player) {
            val inv = getInventory(player)

            player.openInventory(inv)
        }
    }

    object Player {
        // CheckInventory
        // CheckArmour TODO(will be parameterized with an enum class called ArmourType) <T : ArmorType>

        interface AddItem : CommandBase {
            override val requiresPlayer
                get() = true

            fun getItem(): Item

            fun addItem(player: org.bukkit.entity.Player) {
                player.inventory.addItem(getItem().export())
            }
        }

        interface CheckHand : CommandBase {
            override val requiresPlayer
                get() = true

            fun getIds(): Set<String>

            fun funToRun(player: org.bukkit.entity.Player)

            fun checkItems(player: org.bukkit.entity.Player) {
                val ids = getIds()
                val itemStack = player.inventory.itemInMainHand

                if (itemStack.type.isAir) return

                val item = Item(itemStack)
                val id = item.id

                if (ids.contains(id)) funToRun(player)
            }
        }
    }
}
