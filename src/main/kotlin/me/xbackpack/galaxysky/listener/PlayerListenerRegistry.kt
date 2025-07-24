package me.xbackpack.galaxysky.listener

import com.sk89q.worldguard.protection.flags.Flags
import me.xbackpack.galaxysky.item.api.giveItem
import me.xbackpack.galaxysky.item.registry.Pickaxes
import me.xbackpack.galaxysky.item.registry.VanillaItems
import me.xbackpack.galaxysky.message.Message
import me.xbackpack.galaxysky.service.ListenerService
import me.xbackpack.galaxysky.service.LocationService
import me.xbackpack.galaxysky.service.WorldGuardService
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object PlayerListenerRegistry {
    fun init() {
        ListenerService.hookEvent<PlayerJoinEvent> { event ->
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

            player.teleport(LocationService.spawnLocation)
        }

        ListenerService.hookEvent<PlayerQuitEvent> { event ->
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

        ListenerService.hookEvent<EntityShootBowEvent> { event ->
            val projectile = event.projectile

            if (projectile.type != EntityType.ARROW) return@hookEvent

            val player = event.entity as? Player ?: return@hookEvent

            val pvpEnabled = WorldGuardService.getFlag(player, player.location, Flags.PVP)

            if (pvpEnabled) return@hookEvent

            event.isCancelled = true

            player.giveItem(VanillaItems.ARROW)
        }
    }
}
