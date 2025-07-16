package me.xbackpack.galaxysky.command.api.util

import me.xbackpack.galaxysky.message.Message
import me.xbackpack.galaxysky.service.PermissionService
import me.xbackpack.galaxysky.util.sendMessage
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import java.util.UUID
import kotlin.time.Duration
import kotlin.time.TimeMark
import kotlin.time.TimeSource

data class UserCooldown(
    val cooldownDuration: Duration,
) {
    private val cooldowned: MutableMap<UUID, TimeMark> = mutableMapOf()

    fun startCooldown(player: Player): CommandResult {
        if (PermissionService.isStaff(player)) return CommandResult(true)

        if (isOnCooldown(player)) return CommandResult(false)

        cooldowned[player.uniqueId] = TimeSource.Monotonic.markNow()

        return CommandResult(true)
    }

    fun isOnCooldown(player: Player) = cooldowned.contains(player.uniqueId)

    fun sendMessage(player: Player) {
        val uuid = player.uniqueId
        val elapsedNow = cooldowned[uuid]?.elapsedNow() ?: error("Player ${player.name} bypassed isOnCooldown check but is not on cooldown")

        val timeRemaining = (cooldownDuration - elapsedNow).inWholeSeconds

        val msg =
            Message.create {
                text("You can use this command again in $timeRemaining seconds.")

                colour(NamedTextColor.RED)
            }

        player.sendMessage(msg, true)
    }
}
