package me.xbackpack.galaxysky.listener

import me.xbackpack.galaxysky.util.Registry
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object PlayerListenerRegistry : Registry<Listener> {
    override fun init(): List<Listener> {
        Registry.hookEvent<PlayerJoinEvent> { event ->
            val plr = event.player

            if (plr.isGlowing) return@hookEvent
        }

        return emptyList()
    }
}
