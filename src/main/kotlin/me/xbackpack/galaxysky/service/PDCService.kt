package me.xbackpack.galaxysky.service

import me.xbackpack.galaxysky.GalaxySky
import me.xbackpack.galaxysky.enum.item.ItemStatType
import me.xbackpack.galaxysky.enum.player.PlayerStatType
import me.xbackpack.galaxysky.item.api.StatModifier
import org.bukkit.OfflinePlayer
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object PDCService {
    object Item {
        object Stats {
            val statsKey = GalaxySky.createKey("stats")

            operator fun get(item: ItemStack): Map<ItemStatType, Double>? {
                val map = mutableMapOf<ItemStatType, Double>()

                item.persistentDataContainer[statsKey, PersistentDataType.TAG_CONTAINER]
                    ?.let { statContainer ->
                        ItemStatType.entries.forEach { type ->
                            statContainer[type.key, PersistentDataType.DOUBLE]
                                ?.let { map.put(type, it) }
                        }
                    }
                    ?: return null

                return map
            }

            operator fun set(
                item: ItemStack,
                stats: Map<ItemStatType, Double>,
            ) = item.editPersistentDataContainer { pdc ->
                val statsContainer = pdc.adapterContext.newPersistentDataContainer()

                stats.forEach { type, value ->
                    statsContainer[type.key, PersistentDataType.DOUBLE] = value
                }

                pdc[statsKey, PersistentDataType.TAG_CONTAINER] = statsContainer
            }
        }

        object Modifiers {
            val modifiersKey = GalaxySky.createKey("modifiers")

            operator fun get(item: ItemStack): Set<StatModifier>? {
                val modifiers = mutableSetOf<StatModifier>()

                // Get modifiers container
                item.persistentDataContainer[modifiersKey, PersistentDataType.TAG_CONTAINER]
                    ?.let { modifiersContainer ->
                        // Find modifier name
                        modifiersContainer.keys.forEach { modifierKey ->
                            // Get modifier container
                            val modifierContainer =
                                modifiersContainer[modifierKey, PersistentDataType.TAG_CONTAINER] ?: error("Cannot happen")

                            // Find the stats stored in the modifier container
                            val modifiedStats = mutableMapOf<ItemStatType, Double>()

                            ItemStatType.entries.forEach { type ->
                                modifierContainer[type.key, PersistentDataType.DOUBLE]
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

            operator fun set(
                item: ItemStack,
                modifiers: Set<StatModifier>,
            ) = item.editPersistentDataContainer { pdc ->
                // Create a container for all modifiers
                val modifiersContainer = pdc.adapterContext.newPersistentDataContainer()

                // Loop through each modifier
                modifiers.forEach { (key, modifiedStats) ->

                    // Create a container for this specific modifier
                    val modifierContainer = modifiersContainer.adapterContext.newPersistentDataContainer()

                    // Setup stats
                    modifiedStats.forEach { type, value ->
                        modifierContainer[type.key, PersistentDataType.DOUBLE] = value
                    }

                    // Add this modifier to the container of modifiers
                    modifiersContainer[key, PersistentDataType.TAG_CONTAINER] = modifierContainer
                }

                // Add the container of modifiers to the item's persistent data container
                pdc[modifiersKey, PersistentDataType.TAG_CONTAINER] = modifiersContainer
            }
        }

        object Id {
            val key = GalaxySky.createKey("id")

            operator fun get(item: ItemStack) = item.persistentDataContainer[key, PersistentDataType.STRING]

            operator fun set(
                item: ItemStack,
                id: String?,
            ) {
                item.editPersistentDataContainer { pdc ->
                    id?.let {
                        pdc[key, PersistentDataType.STRING] = it
                    } ?: pdc.remove(key)
                }
            }
        }
    }

    object Player {
        object Stats {
            operator fun get(
                player: OfflinePlayer?,
                type: PlayerStatType,
            ) = player?.persistentDataContainer[type.key, PersistentDataType.INTEGER] ?: 0

            fun inc(
                player: org.bukkit.entity.Player,
                type: PlayerStatType,
            ) {
                val previous = get(player, type)

                player.persistentDataContainer[type.key, PersistentDataType.INTEGER] = previous + 1
            }

            fun reset(
                player: org.bukkit.entity.Player,
                type: PlayerStatType,
            ) {
                player.persistentDataContainer[type.key, PersistentDataType.INTEGER] = 0
            }
        }
    }
}
