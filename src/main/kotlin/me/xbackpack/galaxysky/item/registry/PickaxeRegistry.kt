package me.xbackpack.galaxysky.item.registry

import me.xbackpack.galaxysky.GalaxySky
import me.xbackpack.galaxysky.common.Registry
import me.xbackpack.galaxysky.item.api.Item
import me.xbackpack.galaxysky.item.api.StatModifier
import me.xbackpack.galaxysky.item.api.StatType
import me.xbackpack.galaxysky.message.Message
import me.xbackpack.galaxysky.service.LocationService
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

object PickaxeRegistry : Registry {
    lateinit var stonePickaxe1: Item

    private lateinit var file: File

    override fun init() {
        file = GalaxySky.getFile("Items", "pickaxes.yml")
        stonePickaxe1 = getPickaxe("stone_pickaxe_1")
    }

    private fun getPickaxe(idLower: String): Item {
        val config = YamlConfiguration.loadConfiguration(file)

        val pickaxeConfig = config.getConfigurationSection(idLower) ?: error("Couldn't find pickaxe $idLower in the item registry.")

        val nameJson = pickaxeConfig.getString("name") ?: error("Pickaxe $idLower has no name")
        val typeString = pickaxeConfig.getString("type") ?: error("Pickaxe $idLower has no type")
        val regionString = pickaxeConfig.getString("region") ?: error("Pickaxe $idLower has no region")
        val amountInt = pickaxeConfig.getInt("amount")
        val descriptionJsonNullable = pickaxeConfig.getString("description")
        val unbreakableBool = pickaxeConfig.getBoolean("unbreakable")

        val statsNullable = pickaxeConfig.getConfigurationSection("stats")
        val modifiersNullable = pickaxeConfig.getConfigurationSection("modifiers")

        val name = Message.fromJson(nameJson)
        val type = Material.valueOf(typeString)
        val region = LocationService.Region.valueOf(regionString)

        val item =
            Item.create(name, type, region, idLower) {
                amount = amountInt

                descriptionJsonNullable?.let { descriptionJson ->
                    description = Message.fromJson(descriptionJson)
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

                        val operationString = modifier.getString("operation") ?: error("Modifier $key has no operation")

                        val operation = StatType.StatOperation.valueOf(operationString)

                        val modifierStats = mutableMapOf<StatType, Double>()

                        StatType.entries.forEach { stat ->
                            val value = modifier.getDouble(stat.name, 0.0).takeIf { it != 0.0 }

                            value?.let {
                                modifierStats[stat] = it
                            }
                        }

                        statModifiers.add(StatModifier(GalaxySky.createKey(key), modifierStats, operation))
                    }
                }
            }

        return item
    }
}
