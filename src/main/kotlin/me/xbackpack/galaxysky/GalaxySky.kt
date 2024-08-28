package me.xbackpack.galaxysky

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import me.xbackpack.galaxysky.commands.player.AFK
import org.bukkit.plugin.java.JavaPlugin

class GalaxySky : JavaPlugin() {
    override fun onEnable() {
        logger.info("Loaded!")

        val manager = lifecycleManager

        manager.registerEventHandler(LifecycleEvents.COMMANDS) { event ->
            val commands = event.registrar()

            commands.register(
                "afk",
                "Teleports you to the AFK area!",
                AFK(),
            )
        }
    }
}
