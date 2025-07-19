package me.xbackpack.galaxysky.item.api

import me.xbackpack.galaxysky.message.Message
import me.xbackpack.galaxysky.service.LocationService
import org.bukkit.Material

@ItemDsl
interface BaseItem {
    var name: Message
    var material: Material
    var type: ItemType
    var region: LocationService.Region
    var amount: Int
    var description: MutableList<Message>
    var id: String
    var unbreakable: Boolean
    val defaultStats: MutableMap<StatType, Double>
    val statModifiers: MutableSet<StatModifier>
}
