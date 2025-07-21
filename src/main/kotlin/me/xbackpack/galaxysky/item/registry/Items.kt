package me.xbackpack.galaxysky.item.registry

import me.xbackpack.galaxysky.GalaxySky
import me.xbackpack.galaxysky.item.api.Item
import me.xbackpack.galaxysky.item.api.ItemType
import me.xbackpack.galaxysky.item.api.StatModifier
import me.xbackpack.galaxysky.item.api.StatType
import me.xbackpack.galaxysky.message.Message
import me.xbackpack.galaxysky.service.LocationService
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import kotlin.collections.set

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
        val descriptionListJson = itemConfig.getStringList("description")
        val unbreakableBool = itemConfig.getBoolean("unbreakable")

        val statsNullable = itemConfig.getConfigurationSection("stats")
        val modifiersNullable = itemConfig.getConfigurationSection("modifiers")

        val name = Message.fromJson(nameJson)
        val material = Material.valueOf(materialString)
        val type = ItemType.valueOf(typeString)
        val region = LocationService.Region.valueOf(regionString)

        val item =
            Item.create(name, material, type, region, idLower) {
                descriptionListJson.forEach {
                    description.add(Message.fromJson(it))
                }

                unbreakable = unbreakableBool

                statsNullable?.let { stats ->
                    stats.getKeys(false).forEach { key ->
                        val stat = StatType.valueOf(key)
                        val value = stats.getDouble(key)

                        defaultStats[stat] = value
                    }
                }

                modifiersNullable?.let { modifiers ->
                    modifiers.getKeys(false).forEach { key ->
                        val modifier = modifiers.getConfigurationSection(key) ?: error("Modifier $key is not a config section")

                        val modifierStats = mutableMapOf<StatType, Double>()

                        StatType.entries.forEach { stat ->
                            val value = modifier.getDouble(stat.name, 0.0).takeIf { it != 0.0 }

                            value?.let {
                                modifierStats[stat] = it
                            }
                        }

                        statModifiers.add(StatModifier(GalaxySky.createKey(key), modifierStats))
                    }
                }
            }

        return item
    }
}
