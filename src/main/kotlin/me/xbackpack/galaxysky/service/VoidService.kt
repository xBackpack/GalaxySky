package me.xbackpack.galaxysky.service

import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.enum.player.PlayerStatType
import me.xbackpack.galaxysky.fullHeal
import me.xbackpack.galaxysky.isVanished
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.event.player.PlayerMoveEvent

object VoidService {
    private val thresholds =
        mapOf(
            LocationService.WORLD to 70,
            LocationService.NETHER to 4,
            LocationService.END to 32,
            LocationService.AETHER to 70,
        )

    fun onPlayerMove(event: PlayerMoveEvent) {
        if (!event.hasChangedBlock()) return

        val player = event.player

        val worldInfo = thresholds[player.world] ?: return
        if (player.y > worldInfo) return

        if (player.gameMode == GameMode.SPECTATOR) return

        val killer = player.killer

        if (!player.isVanished()) {
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
        }

        player.fullHeal()
        PDCService.PlayerData.Stats.inc(player, PlayerStatType.DEATHS)
        player.teleport(player.respawnLocation ?: LocationService.WORLD_SPAWN)
    }
}
