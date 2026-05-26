package me.xbackpack.galaxysky

import me.clip.placeholderapi.PlaceholderAPI
import me.xbackpack.galaxysky.api.item.Item
import me.xbackpack.galaxysky.api.message.Message
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.attribute.Attribute
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import kotlin.time.Duration

fun Player.isVanished() = PlaceholderAPI.setPlaceholders(this, "%premiumvanish_vanished%") == "yes"

fun Player.fullHeal() {
    fireTicks = 0
    health = getAttribute(Attribute.MAX_HEALTH)?.baseValue ?: 20.0
    foodLevel = 20
    saturation = 20f
}

fun Player.giveItem(item: Item) = giveItem(item.build())

fun Player.giveItem(itemStack: ItemStack) {
    inventory
        .addItem(itemStack)
        .takeIf { it.isNotEmpty() }
        ?.let {
            sendActionBar(
                Message
                    .create {
                        text("Inventory Full!") {
                            colour(NamedTextColor.RED)
                        }
                    }.root,
            )
        }
}

fun CommandSender.sendMessage(message: Message) {
    sendMessage(message.root)
}

inline val Duration.inWholeTicks: Long
    get() = inWholeSeconds * 20
