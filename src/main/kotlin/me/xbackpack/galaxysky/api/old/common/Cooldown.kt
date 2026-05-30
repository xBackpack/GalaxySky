package me.xbackpack.galaxysky.api.old.common

import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.sendMessage
import me.xbackpack.galaxysky.service.LuckPermsService.isStaff
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import java.util.UUID
import kotlin.math.ceil
import kotlin.time.Duration
import kotlin.time.TimeMark
import kotlin.time.TimeSource

@CommandDsl
data class Cooldown(
    val cooldownDuration: Duration,
    val appliesToStaff: Boolean = false,
    val messageOverride: ((Double) -> Message)? = null,
) {
    private val cooldowned = mutableMapOf<UUID, TimeMark>()

    fun startCooldown(player: Player) {
        if (!appliesToStaff && player.isStaff()) return

        if (isOnCooldown(player)) return

        cooldowned[player.uniqueId] = TimeSource.Monotonic.markNow()
    }

    fun isOnCooldown(player: Player): Boolean {
        val uuid = player.uniqueId

        val mark = cooldowned[uuid] ?: return false

        if (cooldownDuration <= mark.elapsedNow()) {
            cooldowned.remove(uuid)
            return false
        }

        return true
    }

    fun sendMessage(player: Player) {
        val uuid = player.uniqueId
        val elapsedNow = cooldowned[uuid]?.elapsedNow() ?: error("Player ${player.name} bypassed isOnCooldown check but is not on cooldown")

        val timeRemainingDuration = cooldownDuration - elapsedNow

        val timeRemaining = ceil(timeRemainingDuration.inWholeMilliseconds / 1000.0)

        val defaultMsg =
            Message.create {
                text("You can use this command again in ${timeRemaining.toString().trimEnd('0').trimEnd('.')} seconds.") {
                    colour(NamedTextColor.RED)
                }
            }

        player.sendMessage(messageOverride?.let { it(timeRemaining) } ?: defaultMsg)
    }
}
