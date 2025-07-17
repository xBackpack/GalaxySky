package me.xbackpack.galaxysky.command.registry.player

import me.xbackpack.galaxysky.command.api.Command
import me.xbackpack.galaxysky.command.api.util.UserCooldown
import me.xbackpack.galaxysky.command.impl.MiscPlayerCommand
import me.xbackpack.galaxysky.command.impl.util.PlayerCommandFunction
import me.xbackpack.galaxysky.common.Registry
import me.xbackpack.galaxysky.item.Item
import me.xbackpack.galaxysky.item.StatType
import me.xbackpack.galaxysky.message.Message
import me.xbackpack.galaxysky.service.LocationService
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import kotlin.time.Duration.Companion.seconds

object PlayerMiscCommandRegistry : Registry<Command> {
    override fun init(): List<Command> {
        val start =
            MiscPlayerCommand.create {
                name = "start"
                description = "Gives the player the starter pickaxe"
                aliases = listOf("begin")
                cooldown = UserCooldown(60.seconds)
                function =
                    PlayerCommandFunction { player, _ ->
                        val item =
                            Item.create(
                                Message.create {
                                    text("Stone Pickaxe") {
                                        colour(NamedTextColor.GRAY)
                                    }

                                    space()

                                    text("1") {
                                        colour(NamedTextColor.AQUA)
                                    }
                                },
                                Material.STONE_PICKAXE,
                                LocationService.Region.BAYSIDE_BEACH,
                            ) {
                                stat(StatType.BREAKING_POWER, 1)
                                stat(StatType.MINING_SPEED, 100)
                                stat(StatType.ORE_FORTUNE, 1)
                            }

                        player.inventory.addItem(item.build())
                    }
            }

        return listOf(start)
    }
}
