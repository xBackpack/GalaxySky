package me.xbackpack.galaxysky.service

import me.xbackpack.galaxysky.api.util.giveItem
import me.xbackpack.galaxysky.api.util.incStat
import me.xbackpack.galaxysky.api.util.isVanished
import me.xbackpack.galaxysky.api.util.message
import me.xbackpack.galaxysky.enum.Colour
import me.xbackpack.galaxysky.enum.player.PlayerStatType
import me.xbackpack.galaxysky.registry.item.Pickaxes
import net.kyori.adventure.text.Component
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
            message {
                section {
                    text("[")

                    text("+") {
                        colour(Colour.GREEN)
                    }

                    text("]")

                    colour(Colour.DARK_GREY)
                }

                space()

                text(player.name) {
                    colour(Colour.GREY)
                }
            }.build(),
        )

        player.setRespawnLocation(LocationService.WORLD_SPAWN, true)

        if (!player.hasPlayedBefore()) {
            event.joinMessage(
                message {
                    section {
                        text("Welcome,")

                        space()

                        text(player.name) {
                            colour(Colour.LIGHT_PURPLE)
                        }

                        text(", to GalaxySky!")

                        space()

                        text("(")

                        text("#${Bukkit.getOfflinePlayers().size}") {
                            colour(Colour.YELLOW)
                        }

                        text(")")

                        colour(Colour.GREY)
                    }
                }.build(),
            )

            val item = Pickaxes.WOODEN_PICKAXE_1

            player.giveItem(item)
        }

        ScoreboardService.create(player)

        player.teleport(LocationService.WORLD_SPAWN)
    }

    fun onPlayerLeave(event: PlayerQuitEvent) {
        val player = event.player

        event.quitMessage(
            message {
                section {
                    text("[")

                    text("-") {
                        colour(Colour.RED)
                    }

                    text("]")

                    colour(Colour.DARK_GREY)
                }

                space()

                text(player.name) {
                    colour(Colour.GREY)
                }
            }.build(),
        )

        ScoreboardService.remove(player)
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

        player.incStat(PlayerStatType.DEATHS)

        val killer =
            player.killer
                ?.also { it.incStat(PlayerStatType.KILLS) }
                ?: player

        if (player.isVanished()) {
            event.deathMessage(Component.empty())
            return
        }

        event.deathMessage(
            message {
                section {
                    text(player.name) {
                        colour(Colour.DARK_RED)
                    }

                    space()

                    when (event.damageSource.damageType) {
                        DamageType.PLAYER_ATTACK -> {
                            text(meleeDeathMessages.random())

                            space()

                            text(killer.name) {
                                colour(Colour.DARK_RED)
                            }
                        }

                        DamageType.ARROW -> {
                            text(rangedDeathMessages.random())

                            space()

                            text(killer.name) {
                                colour(Colour.DARK_RED)
                            }
                        }

                        else -> {
                            text("died a stupid death")
                        }
                    }

                    colour(Colour.DARK_GREY)
                }
            }.build(),
        )
    }

    fun updatePlaytime() {
        Bukkit.getOnlinePlayers().forEach { player ->
            player.incStat(PlayerStatType.PLAYTIME)
        }
    }
}
