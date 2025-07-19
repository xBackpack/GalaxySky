package me.xbackpack.galaxysky.item.api

import io.papermc.paper.datacomponent.DataComponentTypes
import io.papermc.paper.datacomponent.item.ItemAttributeModifiers
import io.papermc.paper.datacomponent.item.ItemLore
import io.papermc.paper.datacomponent.item.TooltipDisplay
import me.xbackpack.galaxysky.common.Builder
import me.xbackpack.galaxysky.message.Message
import me.xbackpack.galaxysky.message.MessageBuilder
import me.xbackpack.galaxysky.message.addLines
import me.xbackpack.galaxysky.message.addMessage
import me.xbackpack.galaxysky.service.LocationService
import me.xbackpack.galaxysky.service.PDCService
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
    override var id: String,
) : BaseItem,
    Builder<ItemStack> {
    override var amount = 1
    override var description: List<Message>? = null
    override var unbreakable = false

    override val defaultStats = mutableMapOf<StatType, Double>()
    override val statModifiers = mutableSetOf<StatModifier>()

    override fun build(): ItemStack {
        val item = ItemStack(type, amount)

        // Assigning the name
        item.setData(DataComponentTypes.ITEM_NAME, name.root)

        // Handling the stats
        val modifiers = ItemAttributeModifiers.itemAttributes()

        defaultStats.forEach { type, value ->
            getModifierFromStat(type, value)?.let { modifiers.addModifier(it.first, it.second) }
        }

        statModifiers.forEach { (key, statBonuses) ->
            statBonuses.forEach { (type, value) ->
                getModifierFromStat(type, value, key)?.let { modifiers.addModifier(it.first, it.second) }
            }
        }

        item.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, modifiers)

        // Setting up the lore
        val lore =
            ItemLore.lore().addMessage {
                if (defaultStats.isNotEmpty()) {
                    text("Stats:") {
                        colour(NamedTextColor.GREEN)
                    }

                    StatType.displayOrder.forEach { type ->
                        defaultStats[type]
                            ?.let { apply(getMessageFromStat(type, it)) }
                    }

                    newline()
                }

                description?.map(::component)

                text(region.name) {
                    colour(region.colour)
                    bold()
                }
            }

        description?.let(lore::addLines)

        item.setData(DataComponentTypes.LORE, lore)

        // Setting up the hidden tooltips
        var tooltipDisplay = TooltipDisplay.tooltipDisplay()

        tooltipDisplay = tooltipDisplay.addHiddenComponents(DataComponentTypes.ATTRIBUTE_MODIFIERS)

        if (unbreakable) {
            item.setData(DataComponentTypes.UNBREAKABLE)
            tooltipDisplay = tooltipDisplay.addHiddenComponents(DataComponentTypes.UNBREAKABLE)
        }

        item.setData(DataComponentTypes.TOOLTIP_DISPLAY, tooltipDisplay)

        // Managing persistent data of item
        PDCService.Item.Stats[item] = defaultStats
        PDCService.Item.Modifiers[item] = statModifiers
        PDCService.Item.Id[item] = id

        return item
    }

    private fun getModifierFromStat(
        type: StatType,
        amount: Double,
        key: NamespacedKey? = null,
    ) = when (type) {
        StatType.MINING_SPEED ->
            Attribute.BLOCK_BREAK_SPEED to
                AttributeModifier(
                    key ?: type.key,
                    round(amount / 100),
                    AttributeModifier.Operation.ADD_NUMBER,
                    EquipmentSlotGroup.MAINHAND,
                )
        else -> null
    }

    private fun getMessageFromStat(
        type: StatType,
        amount: Double,
    ): MessageBuilder.() -> Unit =
        {
            space()

            text("${type.statName}:") {
                colour(NamedTextColor.DARK_GRAY)
            }

            space()

            section {
                when (type.operation) {
                    StatType.StatOperation.ADD_VALUE -> text("+")
                    StatType.StatOperation.MINUS_VALUE -> text("-")
                    StatType.StatOperation.SET_VALUE -> {}
                }

                text(amount.toString().trimEnd('0').trimEnd('.'))

                colour(type.purpose.colour)
            }
        }
}
