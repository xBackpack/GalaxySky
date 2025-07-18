package me.xbackpack.galaxysky.service

import me.xbackpack.galaxysky.GalaxySky
import me.xbackpack.galaxysky.item.api.StatModifier
import me.xbackpack.galaxysky.item.api.StatType
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object PDCService {
    object Item {
        object Stats {
            val statsKey = GalaxySky.createKey("stats")

            operator fun get(item: ItemStack): Map<StatType, Double>? {
                val map = mutableMapOf<StatType, Double>()

                item.persistentDataContainer[statsKey, PersistentDataType.TAG_CONTAINER]
                    ?.let { statContainer ->
                        StatType.entries.forEach { type ->
                            statContainer[type.key, PersistentDataType.DOUBLE]
                                ?.let { map.put(type, it) }
                        }
                    }
                    ?: return null

                return map
            }

            operator fun set(
                item: ItemStack,
                stats: Map<StatType, Double>,
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

            val modifierOperationKey = GalaxySky.createKey("modifier_operation")

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

                            // Get the name of the operation that the modifier does
                            val operationName =
                                modifierContainer[modifierOperationKey, PersistentDataType.STRING]
                                    ?: error("Modifier does not have operation")

                            // Find the stats stored in the modifier container
                            val modifiedStats = mutableMapOf<StatType, Double>()

                            StatType.entries.forEach { type ->
                                modifierContainer[type.key, PersistentDataType.DOUBLE]
                                    ?.let {
                                        modifiedStats[type] = it
                                    }
                            }

                            // Create the modifier and add it to the list of modifiers

                            val operation = StatType.StatOperation.valueOf(operationName)

                            val modifier = StatModifier(modifierKey, modifiedStats, operation)

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
                modifiers.forEach { (key, modifiedStats, operation) ->

                    // Create a container for this specific modifier
                    val modifierContainer = modifiersContainer.adapterContext.newPersistentDataContainer()

                    // Configure this specific modifier to have an operation and the modified stat
                    modifierContainer[modifierOperationKey, PersistentDataType.STRING] = operation.name

                    modifiedStats.forEach { type, value ->
                        modifierContainer[type.key, PersistentDataType.DOUBLE] = value
                    }

                    // Add this modifier to the container of modifiers
                    modifiersContainer[key, PersistentDataType.TAG_CONTAINER] = modifierContainer
                }

                // Add the container of modifiers to the item's persistent data container
                pdc.set(modifiersKey, PersistentDataType.TAG_CONTAINER, modifiersContainer)
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
}
