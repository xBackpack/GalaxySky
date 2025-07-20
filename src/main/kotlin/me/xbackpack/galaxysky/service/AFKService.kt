package me.xbackpack.galaxysky.service

import me.xbackpack.galaxysky.hook.WorldGuardHook
import me.xbackpack.galaxysky.item.api.giveItem
import me.xbackpack.galaxysky.item.registry.Materials
import me.xbackpack.galaxysky.message.Message
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import java.util.UUID
import kotlin.math.ceil
import kotlin.time.TimeMark
import kotlin.time.TimeSource

object AFKService {
    private val times = mutableMapOf<UUID, TimeMark>()
    private val bossbars = mutableMapOf<UUID, BossBar>()

    fun check(player: Player): Boolean {
        val uuid = player.uniqueId

        val regions = WorldGuardHook.getRegionsFromLocation(player.location)

        val afkRegion = WorldGuardHook.getRegion(LocationService.world, "afk")

        if (!regions.contains(afkRegion)) {
            bossbars[uuid]?.let {
                player.hideBossBar(it)
                bossbars.remove(uuid)
            }

            times.remove(uuid)

            return false
        }

        times.putIfAbsent(uuid, TimeSource.Monotonic.markNow())

        showBossbarAndHandlePayouts(player)

        return true
    }

    fun showBossbarAndHandlePayouts(player: Player) {
        val uuid = player.uniqueId

        val elapsed = times[uuid]?.elapsedNow() ?: error("Player $player is not in times dictionary")

        val secondsPassed = ceil(elapsed.inWholeMilliseconds / 1000f) + 1

        val timeUntilNextPayout = ((secondsPassed - 1) % 120) + 1

        val progress = timeUntilNextPayout / 120

        val title =
            Message
                .create {
                    section {
                        text("AFK Time:")

                        space()

                        text(secondsPassed.toInt().toString()) {
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

        if ((secondsPassed != 1f) && (timeUntilNextPayout == 1f)) {
            val item = Materials.AFK_TOKEN

            player.giveItem(item)
        }
    }
}
