package me.xbackpack.galaxysky.service

import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.giveItem
import me.xbackpack.galaxysky.registry.item.Items
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.UUID

object AFKService {
    private val times = mutableMapOf<UUID, Int>()
    private val bossbars = mutableMapOf<UUID, BossBar>()
    private val afkRegion = WorldGuardService.getRegion(LocationService.WORLD, "afk")

    private fun getTitle(seconds: Int) =
        Message
            .create {
                section {
                    text("AFK Time:")

                    space()

                    text(seconds.toString()) {
                        colour(NamedTextColor.GREEN)
                    }

                    text("s")

                    colour(NamedTextColor.LIGHT_PURPLE)
                }
            }.component

    fun update() {
        Bukkit.getOnlinePlayers().forEach { player ->
            val uuid = player.uniqueId

            val occupiedRegions = WorldGuardService.getOccupiedRegions(player.location)

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

    fun updatePlayer(player: Player) {
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
