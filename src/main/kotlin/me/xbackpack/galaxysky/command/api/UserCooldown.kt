package me.xbackpack.galaxysky.command.api

import me.xbackpack.galaxysky.message.Message
import me.xbackpack.galaxysky.message.sendMessage
import me.xbackpack.galaxysky.service.PermissionService
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import java.util.UUID
import kotlin.math.ceil
import kotlin.time.Duration
import kotlin.time.TimeMark
import kotlin.time.TimeSource

data class UserCooldown(
    val cooldownDuration: Duration,
) {
    private val cooldowned = mutableMapOf<UUID, TimeMark>()

    fun startCooldown(player: Player): Boolean {
        if (PermissionService.isStaff(player)) return true

        if (isOnCooldown(player)) return false

        cooldowned[player.uniqueId] = TimeSource.Monotonic.markNow()

        return true
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

        val msg =
            Message.Companion.create {
                text("You can use this command again in ${timeRemaining.toString().trimEnd('0').trimEnd('.')} seconds.") {
                    colour(NamedTextColor.RED)
                }
            }

        player.sendMessage(msg)
    }
}
