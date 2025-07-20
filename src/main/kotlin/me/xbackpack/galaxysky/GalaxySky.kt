package me.xbackpack.galaxysky

import io.papermc.paper.plugin.configuration.PluginMeta
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import me.xbackpack.galaxysky.command.registry.SuperCommandRegistry
import me.xbackpack.galaxysky.hook.LuckPermsHook
import me.xbackpack.galaxysky.hook.PlaceholderHook
import me.xbackpack.galaxysky.item.registry.Materials
import me.xbackpack.galaxysky.item.registry.Pickaxes
import me.xbackpack.galaxysky.listener.PlayerListenerRegistry
import me.xbackpack.galaxysky.service.AFKService
import me.xbackpack.galaxysky.service.LocationService
import me.xbackpack.galaxysky.service.MineService
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.logging.Logger

class GalaxySky : JavaPlugin() {
    override fun onEnable() {
        instance = this
        meta = pluginMeta
        pluginManager = server.pluginManager
        log = logger

        // Every second
        server.scheduler.runTaskTimer(
            this,
            Runnable {
                Bukkit.getOnlinePlayers().forEach(AFKService::check)
            },
            20,
            20,
        )

        // Every 30 seconds
        server.scheduler.runTaskTimer(this, MineService::resetMines, 0, 30 * 20)

        logger.info("Set up scheduled events!")

        // PlaceholderAPI Custom Placeholders
        PlaceholderHook.register()

        // LuckPerms
        LuckPermsHook.init()

        // Item Registries
        Materials.init()
        Pickaxes.init()

        logger.info("Loaded items!")

        // Worlds
        LocationService.init()

        logger.info("Loaded worlds!")

        // Listeners
        PlayerListenerRegistry.init()

        logger.info("Loaded listeners!")

        // Commands
        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { event ->
            val registrar = event.registrar()

            SuperCommandRegistry.get().forEach { (node, description, aliases) ->
                registrar.register(node.build(), description, aliases)
            }
        }

        logger.info("Loaded commands!")

        logger.info("Enabled!")
    }

    companion object {
        lateinit var instance: GalaxySky
        lateinit var meta: PluginMeta
        lateinit var pluginManager: PluginManager
        lateinit var log: Logger

        fun createKey(key: String) = NamespacedKey(instance, key)

        fun getFile(
            directory: String,
            fileName: String,
        ) = File("${instance.dataFolder}${File.separator}$directory", fileName)
    }
}
