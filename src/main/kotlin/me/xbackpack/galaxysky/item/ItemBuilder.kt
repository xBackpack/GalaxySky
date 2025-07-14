package me.xbackpack.galaxysky.item

import me.xbackpack.galaxysky.message.Message
import me.xbackpack.galaxysky.util.Builder
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

@ItemDSL
class ItemBuilder : Builder<ItemStack> {
    private val name = Message(Component.empty())
    private val enchantment = emptySet<Enchantment>()

    override fun build() = ItemStack(Material.AIR)
}
