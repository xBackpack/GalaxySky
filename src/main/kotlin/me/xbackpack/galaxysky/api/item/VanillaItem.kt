package me.xbackpack.galaxysky.api.item

import io.papermc.paper.datacomponent.DataComponentTypes
import io.papermc.paper.datacomponent.item.CustomModelData
import io.papermc.paper.datacomponent.item.ItemLore
import io.papermc.paper.datacomponent.item.TooltipDisplay
import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.api.util.addMessage
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

@ItemDsl
class VanillaItem(
    override val name: Message,
    override val material: Material,
    override var amount: Int,
    override val modelData: String?,
    override val glowing: Boolean,
    override val description: List<Message>,
    override val unbreakable: Boolean,
    override val id: String?,
) : Item {
    override fun build() =
        ItemStack
            .of(material, amount)
            .apply {
                setData(DataComponentTypes.ITEM_NAME, name.build())

                modelData?.let {
                    setData(
                        DataComponentTypes.CUSTOM_MODEL_DATA,
                        CustomModelData.customModelData().addString(it),
                    )
                }

                setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, glowing)

                setData(
                    DataComponentTypes.LORE,
                    ItemLore.lore().addMessage {
                        description.forEach {
                            component(it)
                            newline()
                        }
                    },
                )

                if (unbreakable) {
                    setData(DataComponentTypes.UNBREAKABLE)
                    setData(
                        DataComponentTypes.TOOLTIP_DISPLAY,
                        TooltipDisplay
                            .tooltipDisplay()
                            .addHiddenComponents(DataComponentTypes.UNBREAKABLE),
                    )
                }
            }
}
