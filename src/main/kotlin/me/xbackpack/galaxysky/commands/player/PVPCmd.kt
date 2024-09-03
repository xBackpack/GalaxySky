package me.xbackpack.galaxysky.commands.player

import me.xbackpack.galaxysky.commands.commandTypes.CooldownCommand
import me.xbackpack.galaxysky.commands.commandTypes.ListenerCommand
import me.xbackpack.galaxysky.commands.commandTypes.ToggleableCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent
import java.util.UUID

class PVPCmd :
    CooldownCommand,
    ListenerCommand,
    ToggleableCommand {
    override val commandName = "pvp"
    override val description = "Toggles the player's ability to PVP"
    override val enabledMessage = Component.text("PVP is now enabled!", NamedTextColor.GREEN)
    override val disabledMessage = Component.text("PVP is now disabled!", NamedTextColor.RED)
    override val cooldownDuration = 1200L
    override val cooldownStartMessage = Component.empty()
    override val cooldownEndMessage = Component.empty()
    override val cooldownMessage = Component.text("This command has a 1 minute cooldown! Just a few more seconds ...", NamedTextColor.RED)
    override val toggledPlayers = hashSetOf<UUID>()
    override val playersWithCooldown = hashSetOf<UUID>()
    override val requiresPlayer = true

    @EventHandler
    fun onAttack(event: EntityDamageByEntityEvent) {
        if (event.entity !is Player || event.damager !is Player) return

        val attacker = event.damager as Player
        val victim = event.entity as Player

        if (toggledPlayers.contains(victim.uniqueId)) {
            event.isCancelled = true
            attacker.sendMessage(Component.text("This player has PVP disabled!", NamedTextColor.RED))
        } else if (toggledPlayers.contains(attacker.uniqueId)) {
            event.isCancelled = true
            attacker.sendMessage(Component.text("You have PVP disabled!", NamedTextColor.RED))
        }
    }
}
