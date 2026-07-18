package me.xbackpack.galaxysky.api.item

import io.papermc.paper.datacomponent.DataComponentTypes
import io.papermc.paper.datacomponent.item.CustomModelData
import io.papermc.paper.datacomponent.item.ItemAttributeModifiers
import io.papermc.paper.datacomponent.item.ItemLore
import io.papermc.paper.datacomponent.item.TooltipDisplay
import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.api.util.addMessage
import me.xbackpack.galaxysky.api.util.pow
import me.xbackpack.galaxysky.api.util.setId
import me.xbackpack.galaxysky.api.util.setModifiers
import me.xbackpack.galaxysky.api.util.setStats
import me.xbackpack.galaxysky.enum.Colour
import me.xbackpack.galaxysky.enum.item.ItemRegion
import me.xbackpack.galaxysky.enum.item.ItemStatType
import me.xbackpack.galaxysky.enum.item.ItemType
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.inventory.EquipmentSlotGroup
import org.bukkit.inventory.ItemStack

@ItemDsl
class CustomItem(
    override val name: Message,
    override val material: Material,
    private val type: ItemType,
    private val region: ItemRegion,
    override val id: String,
    override val modelData: String?,
) : Item {
    override var amount = 1
    override var description = listOf<Message>()
    override var unbreakable = false
    override var glowing = false

    var stats = mutableMapOf<ItemStatType, Int>()
    private var statModifiers = mutableSetOf<StatModifier>()

    override fun build(): ItemStack {
        if (material.isAir) return ItemStack.empty()

        val item = ItemStack.of(material, amount)

        // Assigning the name
        item.setData(DataComponentTypes.ITEM_NAME, name.build())

        // Setting up the lore
        val lore =
            ItemLore.lore().addMessage {
                if (stats.isNotEmpty()) {
                    text("Stats:") {
                        colour(Colour.GREEN)
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
                        colour(Colour.GREEN)
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

                text(type.name) {
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
        item.setStats(stats)
        item.setModifiers(statModifiers)
        item.setId(id)

        // Handling attributes
        if (stats.contains(ItemStatType.MINING_SPEED)) {
            val (additive, multiplicative) = doAllTheMaths()

            val attributes =
                ItemAttributeModifiers
                    .itemAttributes()
                    .addModifier(
                        Attribute.MINING_EFFICIENCY,
                        AttributeModifier(
                            ItemStatType.MINING_SPEED.key,
                            additive,
                            AttributeModifier.Operation.ADD_NUMBER,
                            EquipmentSlotGroup.MAINHAND,
                        ),
                    ).addModifier(
                        Attribute.BLOCK_BREAK_SPEED,
                        AttributeModifier(
                            ItemStatType.MINING_SPEED.key2,
                            multiplicative.toDouble() - 1,
                            AttributeModifier.Operation.ADD_NUMBER,
                            EquipmentSlotGroup.MAINHAND,
                        ),
                    ).build()

            item.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, attributes)
        }

        // Handling item model
        modelData?.let { name ->
            item.setData(DataComponentTypes.CUSTOM_MODEL_DATA, CustomModelData.customModelData().addString(name).build())
        }

        return item
    }

    private fun getMessageFromStat(
        type: ItemStatType,
        amount: Int,
    ): Message.() -> Unit =
        {
            space()

            text("${type.statName}:") {
                colour(Colour.DARK_GREY)
            }

            space()

            section {
                if (amount > 0 && type != ItemStatType.BREAKING_POWER) {
                    text("+")
                }

                text(String.format("%,d", amount))

                colour(type.purpose.colour)
            }
        }

    private fun getStatMessageFromModifiers(type: ItemStatType): (Message.() -> Unit)? {
        var finalAmount = 0

        statModifiers.forEach { (_, stats: Map<ItemStatType, Int>) ->
            stats[type]?.let { value ->
                finalAmount += value
            }
        }

        if (finalAmount == 0) return null

        return getMessageFromStat(type, finalAmount)
    }

    private fun doAllTheMaths(): Pair<Double, Int> {
        val miningSpeed = stats[ItemStatType.MINING_SPEED]!!.toDouble() / 100 // n
        val toolConstant = getNaturalStrengthOfTool(material) // b
        val max = 1024 // a

        val k = miningSpeed / max

        val multiplicative = 2.pow(k.toInt()) // y

        val additive = (miningSpeed / multiplicative) - toolConstant // x

        return additive to multiplicative
    }

    companion object {
        fun getNaturalStrengthOfTool(tool: Material) =
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
}
