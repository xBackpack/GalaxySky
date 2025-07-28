package me.xbackpack.galaxysky.service

import com.sk89q.worldguard.protection.flags.Flags
import me.clip.placeholderapi.PlaceholderAPI
import me.xbackpack.galaxysky.GalaxySky
import me.xbackpack.galaxysky.enum.player.PlayerStatType
import me.xbackpack.galaxysky.item.api.giveItem
import me.xbackpack.galaxysky.item.registry.Pickaxes
import me.xbackpack.galaxysky.item.registry.VanillaItems
import me.xbackpack.galaxysky.message.Message
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.damage.DamageType
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerChangedWorldEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent

object ListenerService {
    private val thresholds =
        mapOf(
            LocationService.WORLD to 70,
            LocationService.NETHER to 4,
            LocationService.END to 32,
            LocationService.AETHER to 70,
        )

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

    fun init() {
        hookEvent<PlayerJoinEvent> { event ->
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

                val item = Pickaxes.STONE_PICKAXE_1

                player.giveItem(item)
            }

            player.teleport(LocationService.WORLD_SPAWN)
        }

        hookEvent<PlayerQuitEvent> { event ->
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

        hookEvent<PlayerChangedWorldEvent> { event ->
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

        hookEvent<EntityShootBowEvent> { event ->
            val projectile = event.projectile

            if (projectile.type != EntityType.ARROW) return@hookEvent

            val player = event.entity as? Player ?: return@hookEvent

            val pvpEnabled = WorldGuardService.getFlag(player, player.location, Flags.PVP)

            if (pvpEnabled) return@hookEvent

            event.isCancelled = true

            player.giveItem(VanillaItems.ARROW)
        }

        hookEvent<PlayerMoveEvent> { event ->
            val player = event.player

            val worldInfo = thresholds[player.world] ?: return@hookEvent
            if (player.y >= worldInfo) return@hookEvent

            if (player.gameMode == GameMode.SPECTATOR) return@hookEvent

            val killer = player.killer

            Bukkit.broadcast(
                Message
                    .create {
                        section {
                            text(player.name) {
                                colour(NamedTextColor.DARK_RED)
                            }

                            space()

                            killer
                                ?.takeIf { killer != player }
                                ?.let {
                                    text("was thrown into the void by")

                                    space()

                                    text(killer.name) {
                                        colour(NamedTextColor.DARK_RED)
                                    }
                                } ?: text("fell into the void")

                            colour(NamedTextColor.DARK_GRAY)
                        }
                    }.root,
            )

            player.teleport(player.respawnLocation ?: LocationService.WORLD_SPAWN)
        }

        hookEvent<PlayerDeathEvent> { event ->
            val player = event.player

            val stats = PDCService.Player.Stats

            stats.inc(player, PlayerStatType.DEATHS)

            val killer =
                player.killer
                    ?.also { stats.inc(it, PlayerStatType.KILLS) }
                    ?: player

            PlaceholderAPI
                .setPlaceholders(player, "%premiumvanish_vanished%")
                .takeIf { it == "yes" }
                ?.let {
                    event.deathMessage(Component.empty())
                    return@hookEvent
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
                                else -> text("died a stupid death")
                            }

                            colour(NamedTextColor.DARK_GRAY)
                        }
                    }.root,
            )
        }
    }

    inline fun <reified T : Event> hookEvent(crossinline handler: (T) -> Unit) {
        val listener = object : Listener {}

        GalaxySky.pluginManager.registerEvent(
            T::class.java,
            listener,
            EventPriority.NORMAL,
            { _, event ->
                (event as? T)?.let { handler(it) }
            },
            GalaxySky.instance,
            false,
        )
    }
}
