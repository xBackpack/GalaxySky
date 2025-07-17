package me.xbackpack.galaxysky.item

import io.papermc.paper.datacomponent.DataComponentTypes
import io.papermc.paper.datacomponent.item.ItemAttributeModifiers
import io.papermc.paper.datacomponent.item.ItemLore
import io.papermc.paper.datacomponent.item.TooltipDisplay
import me.xbackpack.galaxysky.common.Builder
import me.xbackpack.galaxysky.message.Message
import me.xbackpack.galaxysky.message.addEmptyLine
import me.xbackpack.galaxysky.message.addMessage
import me.xbackpack.galaxysky.message.addMessages
import me.xbackpack.galaxysky.service.ItemIdService
import me.xbackpack.galaxysky.service.LocationService
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.inventory.EquipmentSlotGroup
import org.bukkit.inventory.ItemStack
import kotlin.math.round

@ItemDsl
class ItemBuilder(
    override var name: Message,
    override var type: Material,
    override var region: LocationService.Region,
) : BaseItem,
    Builder<ItemStack> {
    override var amount = 1
    override var itemDescription = mutableListOf<Message>()
    override var id: String? = null
    override var unbreakable = false

    override val defaultStats = mutableMapOf<StatType, Int>()
    override val statModifiers = mutableSetOf<StatModifier>()

    fun stat(
        type: StatType,
        value: Int,
    ) {
        defaultStats.put(type, value)
    }

    override fun build(): ItemStack {
        val item = ItemStack(type, amount)

        // Assigning the name
        item.setData(DataComponentTypes.ITEM_NAME, name.component)

        // Handling the stats
        val modifiers = ItemAttributeModifiers.itemAttributes()

        defaultStats.forEach { stat, value ->
            addStat(stat, value)?.let { modifiers.addModifier(it.first, it.second) }
        }

        statModifiers.forEach { (stat, key, bonus) ->
            addStat(stat, bonus, key)?.let { modifiers.addModifier(it.first, it.second) }
        }

        item.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, modifiers)

        // Setting up the lore
        var lore = ItemLore.lore()

        if (defaultStats.isNotEmpty()) {
            lore =
                lore.addMessage {
                    text("Stats:") {
                        colour(NamedTextColor.GREEN)
                    }
                }

            defaultStats.forEach { stat, amount ->
                lore =
                    lore.addMessage {
                        space()

                        text("${stat.statName}:") {
                            colour(NamedTextColor.DARK_GRAY)
                        }

                        space()

                        section {
                            when (stat.operation) {
                                StatType.StatOperation.ADD_VALUE -> text("+")
                                StatType.StatOperation.MINUS_VALUE -> text("-")
                                StatType.StatOperation.SET_VALUE -> {}
                            }

                            text(amount.toString())

                            colour(stat.purpose.colour)
                        }
                    }
            }

            lore.addEmptyLine()
        }

        if (itemDescription.isNotEmpty()) {
            lore = lore.addMessages(itemDescription)

            lore = lore.addEmptyLine()
        }

        lore =
            lore.addMessage {
                text(region.regionName.uppercase()) {
                    colour(region.colour)
                    bold()
                }
            }

        item.setData(DataComponentTypes.LORE, lore)

        // Setting up the hidden tooltips
        var tooltipDisplay = TooltipDisplay.tooltipDisplay()

        tooltipDisplay = tooltipDisplay.addHiddenComponents(DataComponentTypes.ATTRIBUTE_MODIFIERS)

        if (unbreakable) {
            item.setData(DataComponentTypes.UNBREAKABLE)
            tooltipDisplay = tooltipDisplay.addHiddenComponents(DataComponentTypes.UNBREAKABLE)
        }

        item.setData(DataComponentTypes.TOOLTIP_DISPLAY, tooltipDisplay)

        ItemIdService[item] = id

        return item
    }

    private fun addStat(
        stat: StatType,
        amount: Int,
        key: NamespacedKey? = null,
    ) = when (stat) {
        StatType.MINING_SPEED ->
            Attribute.BLOCK_BREAK_SPEED to
                AttributeModifier(
                    key ?: stat.key,
                    round(amount / 100.0),
                    AttributeModifier.Operation.ADD_NUMBER,
                    EquipmentSlotGroup.MAINHAND,
                )
        else -> null
    }
}
