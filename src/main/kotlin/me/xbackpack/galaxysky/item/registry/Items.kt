package me.xbackpack.galaxysky.item.registry

import me.xbackpack.galaxysky.GalaxySky
import me.xbackpack.galaxysky.enum.item.ItemRegion
import me.xbackpack.galaxysky.enum.item.ItemStatType
import me.xbackpack.galaxysky.enum.item.ItemType
import me.xbackpack.galaxysky.item.api.Item
import me.xbackpack.galaxysky.item.api.StatModifier
import me.xbackpack.galaxysky.message.Message
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

object Items {
    fun getPickaxe(idLower: String) = getItem(getFile("pickaxes.yml"), idLower)

    fun getMaterial(idLower: String) = getItem(getFile("materials.yml"), idLower)

    fun getVanillaItem(idLower: String) = getItem(getFile("vanilla.yml"), idLower)

    private fun getFile(name: String) = GalaxySky.getFile("Items", name)

    private fun getItem(
        file: File,
        idLower: String,
    ): Item {
        val config = YamlConfiguration.loadConfiguration(file)

        val itemConfig = config.getConfigurationSection(idLower) ?: error("Couldn't find item $idLower in the item registry.")

        val nameJson = itemConfig.getString("name") ?: error("Item $idLower has no name")
        val materialString = itemConfig.getString("material") ?: error("Item $idLower has no material")
        val typeString = itemConfig.getString("type") ?: error("Item $idLower has no type")
        val regionString = itemConfig.getString("region") ?: error("Item $idLower has no region")
        val descriptionListJson = itemConfig.getStringList("description").toList()
        val unbreakableBool = itemConfig.getBoolean("unbreakable")

        val statsNullable = itemConfig.getConfigurationSection("stats")
        val modifiersNullable = itemConfig.getConfigurationSection("modifiers")

        val name = Message.fromJson(nameJson)
        val material = Material.valueOf(materialString)
        val type = ItemType.valueOf(typeString)
        val region = ItemRegion.valueOf(regionString)

        val item =
            Item.create(name, material, type, region, idLower) {
                val tempDescription = mutableListOf<Message>()
                descriptionListJson.forEach {
                    tempDescription.add(Message.fromJson(it))
                }

                description = tempDescription

                unbreakable = unbreakableBool

                statsNullable?.let { stats ->
                    val tempStats = mutableMapOf<ItemStatType, Double>()

                    stats.getKeys(false).forEach { key ->
                        val stat = ItemStatType.valueOf(key)
                        val value = stats.getDouble(key)

                        tempStats[stat] = value
                    }

                    defaultStats = tempStats
                }

                modifiersNullable?.let { modifiers ->
                    val tempModifiers = mutableSetOf<StatModifier>()

                    modifiers.getKeys(false).forEach { key ->
                        val modifier = modifiers.getConfigurationSection(key) ?: error("Modifier $key is not a config section")

                        val modifierStats = mutableMapOf<ItemStatType, Double>()

                        ItemStatType.entries.forEach { stat ->
                            val value = modifier.getDouble(stat.name, 0.0).takeIf { it != 0.0 }

                            value?.let {
                                modifierStats[stat] = it
                            }
                        }

                        tempModifiers.add(StatModifier(GalaxySky.createKey(key), modifierStats))
                    }

                    statModifiers = tempModifiers
                }
            }

        return item
    }
}
