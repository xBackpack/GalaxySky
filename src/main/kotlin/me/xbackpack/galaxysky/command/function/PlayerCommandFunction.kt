package me.xbackpack.galaxysky.command.function

import me.xbackpack.galaxysky.command.common.ArgumentGetter
import me.xbackpack.galaxysky.command.common.CommandDsl
import me.xbackpack.galaxysky.item.api.Item
import me.xbackpack.galaxysky.item.api.giveItem
import me.xbackpack.galaxysky.message.Message
import me.xbackpack.galaxysky.message.MessageBuilder
import me.xbackpack.galaxysky.message.sendMessage
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.InventoryHolder

@CommandDsl
class PlayerCommandFunction(
    val player: Player,
    private val getter: ArgumentGetter,
) : CommandFunction {
    override val functions = mutableListOf<() -> Unit>()

    fun sendMessage(builder: MessageBuilder.() -> Unit) {
        functions.add {
            player.sendMessage(Message.create(builder))
        }
    }

    fun showInv(inventory: InventoryHolder) {
        functions.add {
            player.openInventory(inventory.inventory)
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

    inline fun <reified T : Entity> spawnEntity(settings: T.() -> Unit) {
        player.world
            .spawn(player.location, T::class.java)
            .apply(settings)
    }

    fun updateGameMode(gamemode: GameMode) {
        functions.add {
            player.gameMode = gamemode
        }
    }
}
