package me.xbackpack.galaxysky.api.util

import me.xbackpack.galaxysky.GalaxySky
import me.xbackpack.galaxysky.api.item.StatModifier
import me.xbackpack.galaxysky.enum.item.ItemStatType
import me.xbackpack.galaxysky.enum.player.PlayerStatType
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

val statsKey = GalaxySky.createKey("stats")
val modifiersKey = GalaxySky.createKey("modifiers")
val idKey = GalaxySky.createKey("id")

fun OfflinePlayer?.getStat(type: PlayerStatType) = this?.persistentDataContainer[type.key, PersistentDataType.INTEGER] ?: 0

fun Player.incStat(type: PlayerStatType) {
    persistentDataContainer[type.key, PersistentDataType.INTEGER] = getStat(type) + 1
}

fun Player.resetStat(type: PlayerStatType) {
    persistentDataContainer[type.key, PersistentDataType.INTEGER] = 0
}

fun ItemStack.getStat(stat: ItemStatType) =
    persistentDataContainer[statsKey, PersistentDataType.TAG_CONTAINER]
        ?.let { statContainer ->
            statContainer[stat.key, PersistentDataType.INTEGER]
        } ?: 0

fun ItemStack.setStats(stats: Map<ItemStatType, Int>) =
    editPersistentDataContainer { pdc ->
        val statsContainer = pdc.adapterContext.newPersistentDataContainer()

        stats.forEach { (type, value) ->
            statsContainer[type.key, PersistentDataType.INTEGER] = value
        }

        pdc[statsKey, PersistentDataType.TAG_CONTAINER] = statsContainer
    }

fun ItemStack.getModifiers(): Set<StatModifier>? {
    val modifiers = mutableSetOf<StatModifier>()

    // Get modifiers container
    persistentDataContainer[modifiersKey, PersistentDataType.TAG_CONTAINER]
        ?.let { modifiersContainer ->
            // Find modifier name
            modifiersContainer.keys.forEach { modifierKey ->
                // Get modifier container
                val modifierContainer =
                    modifiersContainer[modifierKey, PersistentDataType.TAG_CONTAINER] ?: error("Cannot happen")

                // Find the stats stored in the modifier container
                val modifiedStats = mutableMapOf<ItemStatType, Int>()

                ItemStatType.entries.forEach { type ->
                    modifierContainer[type.key, PersistentDataType.INTEGER]
                        ?.let {
                            modifiedStats[type] = it
                        }
                }

                // Create the modifier and add it to the list of modifiers
                val modifier = StatModifier(modifierKey, modifiedStats)

                modifiers.add(modifier)
            }
        } ?: return null

    return modifiers
}

fun ItemStack.setModifiers(modifiers: Set<StatModifier>) =
    editPersistentDataContainer { pdc ->
        // Create a container for all modifiers
        val modifiersContainer = pdc.adapterContext.newPersistentDataContainer()

        // Loop through each modifier
        modifiers.forEach { (key, modifiedStats) ->

            // Create a container for this specific modifier
            val modifierContainer = modifiersContainer.adapterContext.newPersistentDataContainer()

            // Setup stats
            modifiedStats.forEach { (type, value) ->
                modifierContainer[type.key, PersistentDataType.INTEGER] = value
            }

            // Add this modifier to the container of modifiers
            modifiersContainer[key, PersistentDataType.TAG_CONTAINER] = modifierContainer
        }

        // Add the container of modifiers to the item's persistent data container
        pdc[modifiersKey, PersistentDataType.TAG_CONTAINER] = modifiersContainer
    }

fun ItemStack.getId() = persistentDataContainer[idKey, PersistentDataType.STRING]

fun ItemStack.setId(id: String) =
    editPersistentDataContainer { pdc ->
        id.let {
            pdc[idKey, PersistentDataType.STRING] = it
        }
    }

fun ItemStack.removeId() = editPersistentDataContainer { it.remove(idKey) }
