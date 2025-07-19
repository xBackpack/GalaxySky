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

object Pickaxes : Registry {
    private val file = GalaxySky.getFile("Items", "pickaxes.yml")

    val STONE_PICKAXE_1 = getPickaxe("stone_pickaxe_1")

    val ADMIN_PICKAXE = getPickaxe("admin_pickaxe")

    override fun init() {}

    private fun getPickaxe(idLower: String): Item {
        val config = YamlConfiguration.loadConfiguration(file)

        val pickaxeConfig = config.getConfigurationSection(idLower) ?: error("Couldn't find pickaxe $idLower in the item registry.")

        val nameJson = pickaxeConfig.getString("name") ?: error("Pickaxe $idLower has no name")
        val typeString = pickaxeConfig.getString("type") ?: error("Pickaxe $idLower has no type")
        val regionString = pickaxeConfig.getString("region") ?: error("Pickaxe $idLower has no region")
        val amountInt = pickaxeConfig.getInt("amount")
        val descriptionListJson = pickaxeConfig.getStringList("description")
        val unbreakableBool = pickaxeConfig.getBoolean("unbreakable")

        val statsNullable = pickaxeConfig.getConfigurationSection("stats")
        val modifiersNullable = pickaxeConfig.getConfigurationSection("modifiers")

        val name = Message.fromJson(nameJson)
        val type = Material.valueOf(typeString)
        val region = LocationService.Region.valueOf(regionString)

        val item =
            Item.create(name, type, region, idLower) {
                amount = amountInt

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
