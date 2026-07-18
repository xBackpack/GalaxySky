package me.xbackpack.galaxysky.api.command

import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.api.util.message
import me.xbackpack.galaxysky.api.util.msg
import me.xbackpack.galaxysky.enum.Colour
import me.xbackpack.galaxysky.service.LuckPermsService.isStaff
import org.bukkit.entity.Player
import java.util.UUID
import kotlin.math.ceil
import kotlin.time.Duration
import kotlin.time.TimeMark
import kotlin.time.TimeSource

class Cooldown(
    private val cooldownDuration: Duration,
    private val appliesToStaff: Boolean = false,
    private val messageOverride: ((Double) -> Message)? = null,
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
            message {
                text("You can use this command again in ${timeRemaining.toString().trimEnd('0').trimEnd('.')} seconds.") {
                    colour(Colour.RED)
                }
            }

        player.msg(messageOverride?.let { it(timeRemaining) } ?: defaultMsg)
    }
}
