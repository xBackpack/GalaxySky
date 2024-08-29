package me.xbackpack.galaxysky.util.commandTypes

import me.xbackpack.galaxysky.GalaxySky
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

interface CooldownCommand : CommandBase {
    override val requiresPlayer
        get() = true
    val activePlayers: MutableSet<UUID>
        get() = ConcurrentHashMap.newKeySet()
    val cooldownDuration: Long
    val cooldownStartMessage: Component
    val cooldownMessage: Component
    val cooldownEndMessage: Component

    fun startCooldown(player: Player) {
        val uuid = player.uniqueId

        val result = activePlayers.add(uuid)

        if (!result) {
            player.sendMessage(cooldownMessage)
            return
        }

        player.sendMessage(cooldownStartMessage)

        Bukkit.getScheduler().runTaskLater(
            GalaxySky.instance,
            Runnable {
                activePlayers.remove(uuid)
                player.sendMessage(cooldownEndMessage)
            },
            cooldownDuration,
        )
    }

    fun isCooldown(player: Player) = activePlayers.contains(player.uniqueId)

    override fun command(
        sender: CommandSender,
        args: List<String>,
    ) {
        val player = sender as Player

        startCooldown(player)
    }
}
