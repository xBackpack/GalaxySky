package me.xbackpack.galaxysky.command.function

import me.xbackpack.galaxysky.command.common.ArgumentGetter
import me.xbackpack.galaxysky.command.common.CommandDsl
import me.xbackpack.galaxysky.item.api.Item
import me.xbackpack.galaxysky.item.api.giveItem
import me.xbackpack.galaxysky.message.MessageBuilder
import me.xbackpack.galaxysky.message.sendMessage
import org.bukkit.Location
import org.bukkit.entity.Player

@CommandDsl
class PlayerCommandFunction(
    private val player: Player,
    private val getter: ArgumentGetter,
) {
    fun runAll() {
        functions.forEach { it() }
    }

    private val functions = mutableListOf<() -> Unit>()

    fun sendMessage(builder: MessageBuilder.() -> Unit) {
        functions.add {
            player.sendMessage(MessageBuilder().apply(builder).build())
        }
    }

    fun teleport(location: Location) {
        functions.add {
            player.teleport(location)
        }
    }

    fun giveItem(item: Item) {
        functions.add {
            player.giveItem(item)
        }
    }
}
