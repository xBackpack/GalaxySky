package me.xbackpack.galaxysky.api.util

import io.papermc.paper.datacomponent.item.ItemLore
import me.clip.placeholderapi.PlaceholderAPI
import me.xbackpack.galaxysky.api.inventory.SingleInventory
import me.xbackpack.galaxysky.api.item.Item
import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.enum.Colour
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Sound
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.ApiStatus
import kotlin.math.pow
import kotlin.time.Duration

inline val Duration.inWholeTicks: Long
    get() = inWholeSeconds * 20

fun Int.pow(k: Int) = toDouble().pow(k).toInt()

fun Player.showInventory(inventory: SingleInventory) {
    openInventory(inventory.inventory)
}

fun Player.isVanished() = PlaceholderAPI.setPlaceholders(this, "%premiumvanish_vanished%") == "yes"

fun Player.fullHeal() {
    fireTicks = 0
    health = getAttribute(Attribute.MAX_HEALTH)?.baseValue ?: 20.0
    foodLevel = 20
    saturation = 20f
}

fun Player.giveItem(item: Item) = giveItem(item.build())

fun Player.giveItems(items: List<Item>) = items.forEach(::giveItem)

fun Player.giveItem(itemStack: ItemStack) {
    inventory
        .addItem(itemStack)
        .takeIf { it.isNotEmpty() }
        ?.let {
            sendActionBar(
                message {
                    text("Inventory Full!") {
                        colour(Colour.RED)
                    }
                }.build(),
            )
            playSound(this, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 0.5f)

            it.forEach { item -> world.dropItemNaturally(location, item.value) }
        }
}

@ApiStatus.Experimental
fun ItemLore.Builder.addMessage(builder: Message.() -> Unit) =
    addLines(
        Message()
            .apply(builder)
            .toLore()
            .map { it.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE) },
    )
