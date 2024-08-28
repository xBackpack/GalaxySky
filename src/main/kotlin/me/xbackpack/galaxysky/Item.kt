package me.xbackpack.galaxysky

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.SkullMeta

open class Item(
    private val item: ItemStack,
) {
    class SkullItem(
        private val item: ItemStack,
    ) : Item(item) {
        var skullOwner: OfflinePlayer?
            get() = (item.itemMeta as SkullMeta).owningPlayer
            set(new) {
                item.itemMeta =
                    (item.itemMeta as SkullMeta).apply {
                        owningPlayer = new
                    }
            }
    }

    constructor(mat: Material) : this(ItemStack(mat))

    var name: Component
        get() = item.displayName()
        set(new) {
            item.itemMeta =
                item.itemMeta.apply {
                    displayName(new)
                }
        }

    var amount: Int
        get() = item.amount
        set(new) {
            item.amount = new
        }

    var unbreakable: Boolean
        get() = item.itemMeta.isUnbreakable
        set(new) {
            item.itemMeta =
                item.itemMeta.apply {
                    isUnbreakable = new
                }
            setFlags(ItemFlag.HIDE_UNBREAKABLE to new)
        }

    var glowing: Boolean
        get() = item.itemMeta.hasEnchant(Enchantment.MENDING)
        set(new) {
            item.itemMeta.addEnchant(Enchantment.MENDING, if (new) 1 else 0, true)
        }

    val tool: Boolean
        get() = item.itemMeta is Damageable

    var lore: MutableList<Component>
        get() = item.lore() ?: mutableListOf()
        set(new) = item.lore(new)

    fun addLore(line: Component) {
        lore.add(line)
    }

    fun removeLast() {
        lore.removeLast()
    }

    fun setFlags(vararg flags: Pair<ItemFlag, Boolean>) {
        item.itemMeta.apply {
            flags.forEach { (flag, enabled) ->
                if (enabled) {
                    addItemFlags(flag)
                } else {
                    removeItemFlags(flag)
                }
            }
            item.setItemMeta(this)
        }
    }

    fun setEnchants(vararg enchants: Pair<Enchantment, Int>) {
        item.itemMeta =
            item.itemMeta.apply {
                enchants.forEach { (enchant, level) ->
                    if (level == 0) {
                        removeEnchant(enchant)
                    } else {
                        addEnchant(enchant, level, true)
                    }
                }
                setFlags(ItemFlag.HIDE_ENCHANTS to hasEnchants())
            }
    }

    fun setAttributes(vararg attributes: Pair<Attribute, AttributeModifier?>) {
        item.itemMeta =
            item.itemMeta.apply {
                attributes.forEach { (attribute, modifier) ->
                    if (modifier == null) {
                        removeAttributeModifier(attribute)
                        return
                    }
                    addAttributeModifier(attribute, modifier)
                }
            }
    }

    fun asSkull(): SkullItem = this as SkullItem

    fun export(): ItemStack = item
}
