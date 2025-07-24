package me.xbackpack.galaxysky.item.api

import io.papermc.paper.datacomponent.DataComponentTypes
import io.papermc.paper.datacomponent.item.ItemLore
import io.papermc.paper.datacomponent.item.TooltipDisplay
import me.xbackpack.galaxysky.common.Builder
import me.xbackpack.galaxysky.enum.item.ItemRegion
import me.xbackpack.galaxysky.enum.item.ItemStatType
import me.xbackpack.galaxysky.enum.item.ItemType
import me.xbackpack.galaxysky.message.Message
import me.xbackpack.galaxysky.message.MessageBuilder
import me.xbackpack.galaxysky.service.PDCService
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

@ItemDsl
class ItemBuilder(
    override var name: Message,
    override var material: Material,
    override var type: ItemType,
    override var region: ItemRegion,
    override var id: String,
) : BaseItem,
    Builder<ItemStack> {
    override var amount = 1
    override var description = listOf<Message>()
    override var unbreakable = false

    override var defaultStats = mapOf<ItemStatType, Double>()
    override var statModifiers = setOf<StatModifier>()

    override fun build(): ItemStack {
        val item = ItemStack(material, amount)

        // Assigning the name
        item.setData(DataComponentTypes.ITEM_NAME, name.root)

        // Setting up the lore
        val lore =
            ItemLore.lore().addMessage {
                if (defaultStats.isNotEmpty()) {
                    text("Stats:") {
                        colour(NamedTextColor.GREEN)
                    }

                    newline()

                    ItemStatType.displayOrder.forEach { type ->
                        defaultStats[type]
                            ?.let { apply(getMessageFromStat(type, it)) }
                            ?.also { newline() }
                    }

                    newline()
                }

                if (statModifiers.isNotEmpty()) {
                    text("Modifiers:") {
                        colour(NamedTextColor.GREEN)
                    }

                    newline()

                    ItemStatType.displayOrder.forEach { type ->
                        getStatMessageFromModifiers(type)
                            ?.let(::apply)
                            ?.also { newline() }
                    }

                    newline()
                }

                description.forEach {
                    component(it)
                    newline()
                }

                if (description.isNotEmpty()) newline()

                section {
                    text(type.name)

                    space()

                    text("|")

                    space()

                    text(region.displayName.uppercase())

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

        // Managing persistent data of item
        PDCService.Item.Stats[item] = defaultStats
        PDCService.Item.Modifiers[item] = statModifiers
        PDCService.Item.Id[item] = id

        return item
    }

    private fun getMessageFromStat(
        type: ItemStatType,
        amount: Double,
    ): MessageBuilder.() -> Unit =
        {
            space()

            text("${type.statName}:") {
                colour(NamedTextColor.DARK_GRAY)
            }

            space()

            section {
                if (amount > 0) {
                    text("+")
                }

                text(amount.toString().trimEnd('0').trimEnd('.'))

                colour(type.purpose.colour)
            }
        }

    private fun getStatMessageFromModifiers(type: ItemStatType): (MessageBuilder.() -> Unit)? {
        var finalAmount = 0.0

        statModifiers.forEach { (_, stats: Map<ItemStatType, Double>) ->
            stats[type]?.let { value ->
                finalAmount += value
            }
        }

        if (finalAmount == 0.0) return null

        return getMessageFromStat(type, finalAmount)
    }

    private fun ItemLore.Builder.addMessage(builder: MessageBuilder.() -> Unit) =
        addLines(
            MessageBuilder()
                .apply(builder)
                .toLore()
                .map { it.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE) },
        )
}
