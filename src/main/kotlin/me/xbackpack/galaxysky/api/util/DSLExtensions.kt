package me.xbackpack.galaxysky.api.util

import me.xbackpack.galaxysky.api.command.Cooldown
import me.xbackpack.galaxysky.api.command.Permission
import me.xbackpack.galaxysky.api.command.Requirement
import me.xbackpack.galaxysky.api.command.branches.Command
import me.xbackpack.galaxysky.api.inventory.SingleInventory
import me.xbackpack.galaxysky.api.item.CustomItem
import me.xbackpack.galaxysky.api.item.VanillaItem
import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.enum.item.ItemRegion
import me.xbackpack.galaxysky.enum.item.ItemType
import org.bukkit.Location
import org.bukkit.Material

fun command(
    name: String,
    description: String,
    requirement: Requirement,
    permission: Permission? = null,
    aliases: List<String> = emptyList(),
    cooldown: Cooldown? = null,
    factory: Command.() -> Unit,
) = Command(name, description, requirement, permission, aliases, cooldown).apply(factory)

fun inventory(
    title: Message,
    rows: Int,
    fillGrey: Boolean,
    cancelClicks: Boolean,
    cancelMisc: Boolean,
    location: Location? = null,
    builder: SingleInventory.() -> Unit,
) = SingleInventory(title, rows, fillGrey, cancelClicks, cancelMisc, location).apply(builder)

fun item(
    name: Message,
    material: Material,
    type: ItemType,
    region: ItemRegion,
    id: String,
    modelData: String? = null,
    builder: CustomItem.() -> Unit = {},
) = CustomItem(name, material, type, region, id, modelData).apply(builder)

fun vanillaItem(
    name: Message,
    material: Material,
    amount: Int = 1,
    modelData: String? = null,
    glowing: Boolean = false,
    description: List<Message> = emptyList(),
    unbreakable: Boolean = false,
    id: String? = null,
) = VanillaItem(name, material, amount, modelData, glowing, description, unbreakable, id)

fun message(builder: Message.() -> Unit) = Message().apply(builder)
