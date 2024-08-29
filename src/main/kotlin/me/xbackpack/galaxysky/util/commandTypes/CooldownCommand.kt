package me.xbackpack.galaxysky.util.commandTypes

import me.xbackpack.galaxysky.GalaxySky
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.UUID

interface CooldownCommand : CommandBase {
    override val requiresPlayer
        get() = true
    val playersWithCooldown: HashSet<UUID>
    val cooldownDuration: Long
    val cooldownStartMessage: Component
    val cooldownMessage: Component
    val cooldownEndMessage: Component

    fun startCooldown(player: Player) {
        if (player.isOp) return

        val uuid = player.uniqueId

        val result = playersWithCooldown.add(uuid)

        if (!result) {
            player.sendMessage(cooldownMessage)
            return
        }

        player.sendMessage(cooldownStartMessage)

        Bukkit.getScheduler().runTaskLater(
            GalaxySky.instance,
            Runnable {
                playersWithCooldown.remove(uuid)
                player.sendMessage(cooldownEndMessage)
            },
            cooldownDuration,
        )
    }

    fun checkCooldown(player: Player): Boolean {
        if (playersWithCooldown.contains(player.uniqueId)) {
            player.sendMessage(cooldownMessage)
            return true
        }

        return false
    }
}
