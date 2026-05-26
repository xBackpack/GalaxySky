package me.xbackpack.galaxysky.service

import me.xbackpack.galaxysky.GalaxySky
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerChangedWorldEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent

object ListenerService {
    fun init() {
        hookEvent<PlayerJoinEvent>(PlayerService::onPlayerJoin)

        hookEvent<PlayerQuitEvent>(PlayerService::onPlayerLeave)

        hookEvent<PlayerChangedWorldEvent>(PlayerService::onPlayerWorldChange)

        hookEvent<PlayerDeathEvent>(PlayerService::onPlayerDeath)

        hookEvent<PlayerMoveEvent>(VoidService::onPlayerMove)

        hookEvent<EntityShootBowEvent>(WorldGuardService::onBowShot)

        hookEvent<BlockBreakEvent>(MiningService::onBlockBreak)
    }

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
