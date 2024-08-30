package me.xbackpack.galaxysky.util.commandTypes

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import java.util.UUID

interface ToggleableCommand : CommandBase {
    override val requiresPlayer: Boolean
        get() = true

    val toggledPlayers: HashSet<UUID>
    val enabledMessage: Component
    val disabledMessage: Component

    fun toggleFeature(player: Player) {
        val uuid = player.uniqueId

        if (toggledPlayers.add(uuid)) {
            player.sendMessage(enabledMessage)
        } else {
            toggledPlayers.remove(uuid)
            player.sendMessage(disabledMessage)
        }
    }
}
