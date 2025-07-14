package me.xbackpack.galaxysky.listener

import me.xbackpack.galaxysky.util.hookEvent
import org.bukkit.event.player.PlayerJoinEvent

class PlayerListenerRegistry : ListenerRegistry {
    fun init() {
        hookEvent<PlayerJoinEvent> { event ->
            val player = event.player
        }
    }
}
