package me.xbackpack.galaxysky.item.api

import me.xbackpack.galaxysky.message.Message
import me.xbackpack.galaxysky.service.LocationService
import org.bukkit.Material

@ItemDsl
interface BaseItem {
    var name: Message
    var type: Material
    var region: LocationService.Region
    var amount: Int
    var itemDescription: MutableList<Message>
    var id: String?
    var unbreakable: Boolean
    val defaultStats: MutableMap<StatType, Int>
    val statModifiers: MutableSet<StatModifier>
}
