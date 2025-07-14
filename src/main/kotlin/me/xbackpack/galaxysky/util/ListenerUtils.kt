package me.xbackpack.galaxysky.util

import me.xbackpack.galaxysky.GalaxySky
import me.xbackpack.galaxysky.listener.ListenerRegistry
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

inline fun <reified T : Event> ListenerRegistry.hookEvent(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    crossinline handler: (T) -> Unit,
) {
    val listener = object : Listener {}

    val plugin = GalaxySky.instance

    plugin.server.pluginManager.registerEvent(
        T::class.java,
        listener,
        priority,
        { _, event ->
            if (event is T) {
                handler(event)
            }
        },
        GalaxySky.instance,
        ignoreCancelled,
    )
}
