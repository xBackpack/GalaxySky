package me.xbackpack.galaxysky.service

import me.xbackpack.galaxysky.GalaxySky
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

object ListenerService {
    inline fun <reified T : Event> hookEvent(crossinline handler: (T) -> Unit) {
        val listener = object : Listener {}

        GalaxySky.pluginManager.registerEvent(
            T::class.java,
            listener,
            EventPriority.NORMAL,
            { _, event ->
                (event as? T)?.let { handler(it) }
            },
            GalaxySky.instance,
            false,
        )
    }
}
