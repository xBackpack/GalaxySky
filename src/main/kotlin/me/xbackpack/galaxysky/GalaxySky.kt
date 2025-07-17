package me.xbackpack.galaxysky

import io.papermc.paper.plugin.configuration.PluginMeta
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import me.xbackpack.galaxysky.command.registry.SuperCommandRegistry
import me.xbackpack.galaxysky.hook.LuckPermsHook
import me.xbackpack.galaxysky.hook.PlaceholderHook
import me.xbackpack.galaxysky.listener.PlayerListenerRegistry
import me.xbackpack.galaxysky.service.LocationService
import org.bukkit.plugin.java.JavaPlugin

class GalaxySky : JavaPlugin() {
    override fun onEnable() {
        instance = this
        meta = pluginMeta

        // PlaceholderAPI Custom Placeholders
        PlaceholderHook.register()

        // LuckPerms
        LuckPermsHook.init()

        // Worlds
        LocationService.init()

        logger.info("Loaded worlds!")

        // Listeners
        PlayerListenerRegistry.init()

        logger.info("Loaded listeners!")

        // Commands
        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { event ->
            val registrar = event.registrar()

            SuperCommandRegistry.init().forEach { (node, description, aliases) ->
                registrar.register(node.build(), description, aliases)
            }
        }

        logger.info("Loaded commands!")

        logger.info("Enabled!")
    }

    companion object {
        lateinit var instance: GalaxySky
        lateinit var meta: PluginMeta
    }
}
