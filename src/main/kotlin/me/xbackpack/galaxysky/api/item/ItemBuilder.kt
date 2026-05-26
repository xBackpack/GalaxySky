package me.xbackpack.galaxysky.api.item

import io.papermc.paper.datacomponent.DataComponentTypes
import io.papermc.paper.datacomponent.item.ItemAttributeModifiers
import io.papermc.paper.datacomponent.item.ItemLore
import io.papermc.paper.datacomponent.item.TooltipDisplay
import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.api.message.MessageBuilder
import me.xbackpack.galaxysky.enum.item.ItemRegion
import me.xbackpack.galaxysky.enum.item.ItemStatType
import me.xbackpack.galaxysky.enum.item.ItemType
import me.xbackpack.galaxysky.service.PDCService
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.inventory.EquipmentSlotGroup
import org.bukkit.inventory.ItemStack

@ItemDsl
class ItemBuilder(
    override var name: Message,
    override var material: Material,
    override var type: ItemType,
    override var region: ItemRegion,
    override var id: String,
) : BaseItem {
    override var amount = 1
    override var description = mutableListOf<Message>()
    override var unbreakable = false
    override var glowing = false

    override var stats = mutableMapOf<ItemStatType, Int>()
    override var statModifiers = mutableSetOf<StatModifier>()

    fun build(): ItemStack {
        val item = ItemStack(material, amount)

        // Assigning the name
        item.setData(DataComponentTypes.ITEM_NAME, name.root)

        // Setting up the lore
        val lore =
            ItemLore.lore().addMessage {
                if (stats.isNotEmpty()) {
                    text("Stats:") {
                        colour(NamedTextColor.GREEN)
                    }

                    newline()

                    ItemStatType.displayOrder.forEach { type ->
                        stats[type]
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

        if (glowing) {
            item.setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)
        }

        item.setData(DataComponentTypes.TOOLTIP_DISPLAY, tooltipDisplay)

        // Managing persistent data of item
        PDCService.ItemData.Stats[item] = stats
        PDCService.ItemData.Modifiers[item] = statModifiers
        PDCService.ItemData.Id[item] = id

        // Handling attributes
        if (stats.contains(ItemStatType.MINING_SPEED)) {
            val value = stats[ItemStatType.MINING_SPEED]!!
            val attributes =
                ItemAttributeModifiers
                    .itemAttributes()
                    .addModifier(
                        Attribute.MINING_EFFICIENCY,
                        AttributeModifier(
                            ItemStatType.MINING_SPEED.key,
                            value.toDouble() - getNaturalStrengthOfTool(material),
                            AttributeModifier.Operation.ADD_NUMBER,
                            EquipmentSlotGroup.MAINHAND,
                        ),
                    ).build()

            item.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, attributes)
        }

        return item
    }

    private fun getMessageFromStat(
        type: ItemStatType,
        amount: Int,
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
        var finalAmount = 0

        statModifiers.forEach { (_, stats: Map<ItemStatType, Int>) ->
            stats[type]?.let { value ->
                finalAmount += value
            }
        }

        if (finalAmount == 0) return null

        return getMessageFromStat(type, finalAmount)
    }

    private fun ItemLore.Builder.addMessage(builder: MessageBuilder.() -> Unit) =
        addLines(
            MessageBuilder()
                .apply(builder)
                .toLore()
                .map { it.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE) },
        )

    private fun getNaturalStrengthOfTool(tool: Material) =
        when (tool) {
            Material.WOODEN_PICKAXE -> 2
            Material.STONE_PICKAXE -> 4
            Material.COPPER_PICKAXE -> 5
            Material.IRON_PICKAXE -> 6
            Material.DIAMOND_PICKAXE -> 8
            Material.NETHERITE_PICKAXE -> 9
            Material.GOLDEN_PICKAXE -> 12
            else -> 0
        }
}
