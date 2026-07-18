package me.xbackpack.galaxysky.api.item

import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.api.util.vanillaItem
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

interface Item {
    val name: Message
    val material: Material
    var amount: Int
    val modelData: String?
    val glowing: Boolean
    val description: List<Message>
    val unbreakable: Boolean
    val id: String?

    fun build(): ItemStack

    companion object {
        val AIR = vanillaItem(Message.empty(), Material.AIR)
    }
}
