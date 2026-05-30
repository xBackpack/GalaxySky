package me.xbackpack.galaxysky.api.old.function

import me.xbackpack.galaxysky.api.item.Item
import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.api.message.MessageBuilder
import me.xbackpack.galaxysky.api.old.common.ArgumentGetter
import me.xbackpack.galaxysky.api.old.common.CommandDsl
import me.xbackpack.galaxysky.giveItem
import me.xbackpack.galaxysky.sendMessage
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

    fun sendMessage(
        target: Player = player,
        builder: MessageBuilder.() -> Unit,
    ) {
        functions.add {
            target.sendMessage(Message.create(builder))
        }
    }

    fun showInv(
        inventory: InventoryHolder,
        target: Player = player,
    ) {
        functions.add {
            target.openInventory(inventory.inventory)
        }
    }

    fun teleport(
        location: Location,
        target: Player = player,
    ) {
        functions.add {
            target.teleport(location)
        }
    }

    fun giveItem(
        item: Item,
        target: Player = player,
    ) {
        functions.add {
            target.giveItem(item)
        }
    }

    inline fun <reified T : Entity> spawnEntity(
        target: Player = player,
        crossinline settings: T.() -> Unit,
    ) {
        functions.add {
            target.world
                .spawn(player.location, T::class.java)
                .apply(settings)
        }
    }

    fun updateGameMode(
        gamemode: GameMode,
        target: Player = player,
    ) {
        functions.add {
            target.gameMode = gamemode
        }
    }

    fun updateFlyingSpeed(
        new: Int,
        target: Player = player,
    ) {
        functions.add {
            target.flySpeed = (new.toFloat() / 10)
        }
    }
}
