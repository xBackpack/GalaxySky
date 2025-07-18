package me.xbackpack.galaxysky.listener

import me.xbackpack.galaxysky.GalaxySky
import me.xbackpack.galaxysky.common.Registry
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object PlayerListenerRegistry : Registry {
    override fun init() {
        hookEvent<PlayerJoinEvent> { event ->
            val plr = event.player

            plr.sendMessage("You joined?")
        }
    }

    private inline fun <reified T : Event> hookEvent(
        priority: EventPriority = EventPriority.NORMAL,
        ignoreCancelled: Boolean = false,
        crossinline handler: (T) -> Unit,
    ) {
        val listener = object : Listener {}

        GalaxySky.pluginManager.registerEvent(
            T::class.java,
            listener,
            priority,
            { _, event ->
                (event as? T)?.let { handler(it) }
            },
            GalaxySky.instance,
            ignoreCancelled,
        )
    }
}
