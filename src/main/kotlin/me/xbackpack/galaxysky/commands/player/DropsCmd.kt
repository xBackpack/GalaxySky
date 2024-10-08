package me.xbackpack.galaxysky.commands.player

import me.xbackpack.galaxysky.commands.commandTypes.CooldownCommand
import me.xbackpack.galaxysky.commands.commandTypes.ListenerCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerDropItemEvent
import java.util.UUID

class DropsCmd :
    CooldownCommand,
    ListenerCommand {
    override val commandName = "drops"
    override val description = "Toggles the player's ability to drop items"
    override val playersWithCooldown = hashSetOf<UUID>()
    override val cooldownDuration = 200L
    override val cooldownStartMessage = Component.text("You can now drop items for the next 10 seconds!", NamedTextColor.GREEN)
    override val cooldownMessage = Component.text("You can already drop items!", NamedTextColor.RED)
    override val cooldownEndMessage = Component.text("You can no longer drop items!", NamedTextColor.RED)

    @EventHandler
    fun onDrop(event: PlayerDropItemEvent) {
        if (event.player.gameMode != GameMode.SURVIVAL) return

        if (!checkCooldown(event.player)) {
            event.isCancelled = true
            event.player.sendMessage(Component.text("Use /drops to drop items!", NamedTextColor.RED))
        }
    }
}
