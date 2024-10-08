package me.xbackpack.galaxysky.commands.commandTypes

import me.xbackpack.galaxysky.GalaxySky
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.UUID

interface CooldownCommand : CommandBase {
    override val requiresPlayer: Boolean
        get() = true

    val playersWithCooldown: HashSet<UUID>
    val cooldownDuration: Long
    val cooldownStartMessage: Component?
        get() = null
    val cooldownMessage: Component
        get() =
            Component
                .text("This command has a ${cooldownDuration / 20} second cooldown. Just a few more seconds...")
                .color(NamedTextColor.RED)
    val cooldownEndMessage: Component?
        get() = null

    fun startCooldown(player: Player) {
        val uuid = player.uniqueId

        val result = playersWithCooldown.add(uuid)

        if (!result) {
            player.sendMessage(cooldownMessage)
            return
        }

        cooldownStartMessage?.let { player.sendMessage(it) }

        Bukkit.getScheduler().runTaskLater(
            GalaxySky.instance,
            Runnable {
                playersWithCooldown.remove(uuid)
                cooldownEndMessage?.let { player.sendMessage(it) }
            },
            cooldownDuration,
        )
    }

    fun checkCooldown(player: Player): Boolean = playersWithCooldown.contains(player.uniqueId)
}
