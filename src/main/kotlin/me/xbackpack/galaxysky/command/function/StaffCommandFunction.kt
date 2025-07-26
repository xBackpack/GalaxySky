package me.xbackpack.galaxysky.command.function

import me.xbackpack.galaxysky.command.common.Argument
import me.xbackpack.galaxysky.command.common.CommandDsl
import me.xbackpack.galaxysky.command.common.PlayerFunction
import me.xbackpack.galaxysky.item.api.Item
import me.xbackpack.galaxysky.item.api.giveItem
import me.xbackpack.galaxysky.message.Message
import me.xbackpack.galaxysky.message.sendMessage
import org.bukkit.Location
import org.bukkit.entity.Player

@CommandDsl
class StaffCommandFunction {
    fun runAll(
        player: Player,
        args: List<Argument>,
    ) {
        functions.forEach { it.execute(player, args) }
    }

    private val functions = mutableListOf<PlayerFunction<Unit>>()

    fun sendMessage(messageFactory: PlayerFunction<Message>) {
        functions.add { player, args ->
            player.sendMessage(messageFactory.execute(player, args))
        }
    }

    fun teleport(locationFactory: PlayerFunction<Location>) {
        functions.add { player, args ->
            player.teleport(locationFactory.execute(player, args))
        }
    }

    fun giveItem(itemFactory: PlayerFunction<Item>) {
        functions.add { player, args ->
            player.giveItem(itemFactory.execute(player, args))
        }
    }
}
