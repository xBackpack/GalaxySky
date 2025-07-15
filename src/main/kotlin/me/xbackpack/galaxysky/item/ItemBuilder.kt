package me.xbackpack.galaxysky.item

import io.papermc.paper.datacomponent.DataComponentTypes
import io.papermc.paper.datacomponent.item.ItemEnchantments
import io.papermc.paper.datacomponent.item.TooltipDisplay
import me.xbackpack.galaxysky.message.Message
import me.xbackpack.galaxysky.util.Builder
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

@ItemDSL
class ItemBuilder : Builder<ItemStack> {
    private lateinit var name: Message
    private lateinit var material: Material
    private var amount = 1

    private var enchantments = mutableMapOf<Enchantment, Int>()

    fun enchant(ench: Pair<Enchantment, Int>) {
        enchantments[ench.first] = ench.second
    }

    override fun build(): ItemStack {
        val item = ItemStack(material, amount)

        var tooltipDisplay = TooltipDisplay.tooltipDisplay()

        item.setData(DataComponentTypes.ITEM_NAME, name.component)

        if (enchantments.isNotEmpty()) {
            item.setData(DataComponentTypes.ENCHANTMENTS, ItemEnchantments.itemEnchantments(enchantments))
            tooltipDisplay = tooltipDisplay.addHiddenComponents(DataComponentTypes.ENCHANTMENTS)
        }

        item.setData(DataComponentTypes.TOOLTIP_DISPLAY, tooltipDisplay)

        return item
    }
}
