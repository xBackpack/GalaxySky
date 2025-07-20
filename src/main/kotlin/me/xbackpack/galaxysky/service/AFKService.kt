package me.xbackpack.galaxysky.service

import me.xbackpack.galaxysky.hook.WorldGuardHook
import me.xbackpack.galaxysky.message.Message
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import java.util.UUID
import kotlin.time.TimeMark
import kotlin.time.TimeSource

object AFKService {
    private val times = mutableMapOf<UUID, TimeMark>()
    private val bossbars = mutableMapOf<UUID, BossBar>()

    fun check(player: Player): Boolean {
        val regions = WorldGuardHook.getRegionsFromLocation(player.location)

        val afkRegion = WorldGuardHook.getRegion(LocationService.world, "afk")

        if (!regions.contains(afkRegion)) {
            hideBossbar(player)

            return false
        }

        times.putIfAbsent(player.uniqueId, TimeSource.Monotonic.markNow())

        showBossbar(player)

        return true
    }

    fun showBossbar(player: Player) {
        val uuid = player.uniqueId

        val elapsed = times[uuid]?.elapsedNow() ?: error("Player $player is not in times dictionary")

        val secondsPassed = elapsed.inWholeSeconds

        val progress = secondsPassed % 5f

        val title =
            Message
                .create {
                    section {
                        text("AFK Time")

                        text("$secondsPassed") {
                            colour(NamedTextColor.AQUA)
                        }

                        text("s")

                        colour(NamedTextColor.LIGHT_PURPLE)
                    }
                }

        var bossbar = BossBar.bossBar(title.root, progress, BossBar.Color.PURPLE, BossBar.Overlay.PROGRESS)

        bossbars[uuid]?.let { bossbar = it.progress(progress) }

        bossbars[uuid] = bossbar

        player.showBossBar(bossbar)
    }

    fun hideBossbar(player: Player) {
        val uuid = player.uniqueId

        bossbars[uuid]?.let(player::hideBossBar)
    }
}
