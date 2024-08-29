package me.xbackpack.galaxysky.util.commandTypes

import me.xbackpack.galaxysky.GalaxySky
import org.bukkit.event.Listener

interface ListenerCommand : Listener {
    fun registerListener() {
        GalaxySky.instance.server.pluginManager
            .registerEvents(this, GalaxySky.instance)
    }
}
