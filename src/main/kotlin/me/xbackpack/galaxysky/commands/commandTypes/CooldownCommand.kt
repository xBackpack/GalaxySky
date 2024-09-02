package me.xbackpack.galaxysky.commands.commandTypes

import me.xbackpack.galaxysky.GalaxySky
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.UUID

interface CooldownCommand : CommandBase {
    override val requiresPlayer: Boolean
        get() = true

    val playersWithCooldown: HashSet<UUID>
    val cooldownDuration: Long
    val cooldownStartMessage: Component
    val cooldownMessage: Component
    val cooldownEndMessage: Component

    fun startCooldown(player: Player) {
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

    fun checkCooldown(player: Player): Boolean = playersWithCooldown.contains(player.uniqueId)
}
