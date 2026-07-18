package me.xbackpack.galaxysky.service

import me.xbackpack.galaxysky.api.util.giveItem
import me.xbackpack.galaxysky.api.util.message
import me.xbackpack.galaxysky.enum.Colour
import me.xbackpack.galaxysky.registry.item.Items
import me.xbackpack.galaxysky.service.WorldGuardService.getOccupiedRegions
import me.xbackpack.galaxysky.service.WorldGuardService.getRegion
import net.kyori.adventure.bossbar.BossBar
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.UUID

object AFKService {
    private val times = mutableMapOf<UUID, Int>()
    private val bossbars = mutableMapOf<UUID, BossBar>()
    private val afkRegion = LocationService.WORLD.getRegion("afk")

    private fun getTitle(seconds: Int) =
        message {
            section {
                text("AFK Time:")

                space()

                text(seconds.toString()) {
                    colour(Colour.GREEN)
                }

                text("s")

                colour(Colour.LIGHT_PURPLE)
            }
        }.build()

    fun update() {
        Bukkit.getOnlinePlayers().forEach { player ->
            val uuid = player.uniqueId

            val occupiedRegions = player.location.getOccupiedRegions()

            if (occupiedRegions.contains(afkRegion)) {
                updatePlayer(player)

                return
            }

            bossbars[uuid]?.let {
                player.hideBossBar(it)
                bossbars.remove(uuid)
            }
        }
    }

    private fun updatePlayer(player: Player) {
        val uuid = player.uniqueId

        val seconds = times.compute(uuid) { _, seconds -> (seconds ?: 0) + 1 } ?: 0

        val timeUntilNextPayout = (seconds - 1) % 120 + 1

        val progress = timeUntilNextPayout / 120f

        val title = getTitle(seconds)

        bossbars[uuid]
            ?.name(title)
            ?.progress(progress)
            ?: let {
                val bossbar = BossBar.bossBar(title, progress, BossBar.Color.PURPLE, BossBar.Overlay.NOTCHED_12)

                bossbars[uuid] = bossbar

                player.showBossBar(bossbar)
            }

        if (seconds != 1 && timeUntilNextPayout == 1) {
            val item = Items.AFK_TOKEN

            player.giveItem(item)
        }
    }
}
