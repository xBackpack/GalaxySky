package me.xbackpack.galaxysky.api.item

import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.enum.item.ItemRegion
import me.xbackpack.galaxysky.enum.item.ItemStatType
import me.xbackpack.galaxysky.enum.item.ItemType
import org.bukkit.Material

@ItemDsl
interface BaseItem {
    var name: Message
    var material: Material
    var type: ItemType
    var region: ItemRegion
    var amount: Int
    var description: List<Message>
    var id: String
    var unbreakable: Boolean
    var glowing: Boolean
    var stats: MutableMap<ItemStatType, Int>
    var statModifiers: MutableSet<StatModifier>
}
