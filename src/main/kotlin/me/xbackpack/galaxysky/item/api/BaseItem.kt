package me.xbackpack.galaxysky.item.api

import me.xbackpack.galaxysky.enum.item.ItemRegion
import me.xbackpack.galaxysky.enum.item.ItemStatType
import me.xbackpack.galaxysky.enum.item.ItemType
import me.xbackpack.galaxysky.message.Message
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
    var defaultStats: Map<ItemStatType, Double>
    var statModifiers: Set<StatModifier>
}
