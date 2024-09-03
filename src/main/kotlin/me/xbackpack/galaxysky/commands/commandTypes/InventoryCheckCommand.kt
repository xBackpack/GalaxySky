package me.xbackpack.galaxysky.commands.commandTypes

import me.xbackpack.galaxysky.util.Item
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

interface InventoryCheckCommand : CommandBase {
    override val requiresPlayer: Boolean
        get() = true

    fun getItems(player: Player): Array<ItemStack>

    fun getIds(): Set<String>

    fun funToRun(player: Player)

    fun checkItems(player: Player) {
        val ids = getIds()

        if (getItems(player).any { ids.contains(Item(it).id) }) funToRun(player)
    }
}
