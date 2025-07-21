package me.xbackpack.galaxysky.service

import me.xbackpack.galaxysky.item.api.giveItem
import me.xbackpack.galaxysky.item.registry.Materials
import me.xbackpack.galaxysky.message.Message
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import java.util.UUID

object AFKService {
    private val times = mutableMapOf<UUID, Int>()
    private val bossbars = mutableMapOf<UUID, BossBar>()

    fun check(player: Player): Boolean {
        val uuid = player.uniqueId

        val regions = WorldGuardService.getRegionsFromLocation(player.location)

        val afkRegion = WorldGuardService.getRegion(LocationService.world, "afk")

        if (!regions.contains(afkRegion)) {
            bossbars[uuid]?.let {
                player.hideBossBar(it)
                bossbars.remove(uuid)
            }

            times.remove(uuid)

            return false
        }

        times.putIfAbsent(uuid, 0)

        times.computeIfPresent(uuid) { _, seconds -> seconds + 1 }

        showBossbarAndHandlePayouts(player)

        return true
    }

    fun showBossbarAndHandlePayouts(player: Player) {
        val uuid = player.uniqueId

        val seconds = times[uuid] ?: error("Player $player is not in times dictionary")

        val timeUntilNextPayout = ((seconds - 1) % 120) + 1

        val progress = timeUntilNextPayout / 120f

        val title =
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
                }.root

        bossbars[uuid]
            ?.name(title)
            ?.progress(progress)
            ?: let {
                val bossbar = BossBar.bossBar(title, progress, BossBar.Color.PURPLE, BossBar.Overlay.NOTCHED_12)

                bossbars[uuid] = bossbar

                player.showBossBar(bossbar)
            }

        if ((seconds != 1) && (timeUntilNextPayout == 1)) {
            val item = Materials.AFK_TOKEN

            player.giveItem(item)
        }
    }
}
