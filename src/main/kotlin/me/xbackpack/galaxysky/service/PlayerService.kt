package me.xbackpack.galaxysky.service

import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.enum.player.PlayerStatType
import me.xbackpack.galaxysky.giveItem
import me.xbackpack.galaxysky.isVanished
import me.xbackpack.galaxysky.registry.item.Pickaxes
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.damage.DamageType
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerChangedWorldEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object PlayerService {
    private val meleeDeathMessages =
        listOf(
            "was brutally murdered by",
            "died a traitors death by the hands of",
            "was ended by",
            "stopped living due to",
            "didn't want to live in the same world as",
        )

    private val rangedDeathMessages =
        listOf(
            "was assassinated by",
            "was disemboweled by",
            "was terminated by",
            "was punctured by",
            "was relieved of their organs by",
        )

    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player

        event.joinMessage(
            Message
                .create {
                    section {
                        text("[")

                        text("+") {
                            colour(NamedTextColor.GREEN)
                        }

                        text("]")

                        colour(NamedTextColor.DARK_GRAY)
                    }

                    space()

                    text(player.name) {
                        colour(NamedTextColor.GRAY)
                    }
                }.root,
        )

        player.setRespawnLocation(LocationService.WORLD_SPAWN, true)

        if (!player.hasPlayedBefore()) {
            event.joinMessage(
                Message
                    .create {
                        section {
                            text("Welcome,")

                            space()

                            text(player.name) {
                                colour(NamedTextColor.LIGHT_PURPLE)
                            }

                            text(", to GalaxySky!")

                            space()

                            text("(")

                            text("#${Bukkit.getOfflinePlayers().size}") {
                                colour(NamedTextColor.YELLOW)
                            }

                            text(")")

                            colour(NamedTextColor.GRAY)
                        }
                    }.root,
            )

            val item = Pickaxes.BaysideBeach.STONE_PICKAXE_1

            player.giveItem(item)
        }

        player.teleport(LocationService.WORLD_SPAWN)
    }

    fun onPlayerLeave(event: PlayerQuitEvent) {
        val player = event.player

        event.quitMessage(
            Message
                .create {
                    section {
                        text("[")

                        text("-") {
                            colour(NamedTextColor.RED)
                        }

                        text("]")

                        colour(NamedTextColor.DARK_GRAY)
                    }

                    space()

                    text(player.name) {
                        colour(NamedTextColor.GRAY)
                    }
                }.root,
        )
    }

    fun onPlayerWorldChange(event: PlayerChangedWorldEvent) {
        val player = event.player

        player.setRespawnLocation(
            when (player.world.name) {
                "world_nether" -> LocationService.NETHER_SPAWN
                "world_the_end" -> LocationService.END_SPAWN
                "world_aether" -> LocationService.AETHER_SPAWN
                "world_staff" -> LocationService.STAFF_WORLD_SPAWN
                else -> LocationService.WORLD_SPAWN
            },
            true,
        )
    }

    fun onPlayerDeath(event: PlayerDeathEvent) {
        val player = event.player

        val stats = PDCService.PlayerData.Stats

        stats.inc(player, PlayerStatType.DEATHS)

        val killer =
            player.killer
                ?.also { stats.inc(it, PlayerStatType.KILLS) }
                ?: player

        if (player.isVanished()) {
            event.deathMessage(Component.empty())
            return
        }

        event.deathMessage(
            Message
                .create {
                    section {
                        text(player.name) {
                            colour(NamedTextColor.DARK_RED)
                        }

                        space()

                        when (event.damageSource.damageType) {
                            DamageType.PLAYER_ATTACK -> {
                                text(meleeDeathMessages.random())

                                space()

                                text(killer.name) {
                                    colour(NamedTextColor.DARK_RED)
                                }
                            }

                            DamageType.ARROW -> {
                                text(rangedDeathMessages.random())

                                space()

                                text(killer.name) {
                                    colour(NamedTextColor.DARK_RED)
                                }
                            }

                            else -> {
                                text("died a stupid death")
                            }
                        }

                        colour(NamedTextColor.DARK_GRAY)
                    }
                }.root,
        )
    }
}
