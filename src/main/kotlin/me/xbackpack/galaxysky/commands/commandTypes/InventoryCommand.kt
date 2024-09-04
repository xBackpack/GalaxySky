package me.xbackpack.galaxysky.commands.commandTypes

import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

object InventoryCommand {
    interface Create : CommandBase {
        override val requiresPlayer
            get() = true

        fun getInventory(player: Player): Inventory

        fun openInventory(player: Player) {
            val inv = getInventory(player)

            player.openInventory(inv)
        }
    }
}
