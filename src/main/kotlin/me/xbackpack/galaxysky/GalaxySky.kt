package me.xbackpack.galaxysky

import io.papermc.paper.plugin.configuration.PluginMeta
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import me.xbackpack.galaxysky.command.registry.SuperCommandRegistry
import me.xbackpack.galaxysky.hook.LuckPermsHook
import me.xbackpack.galaxysky.hook.PlaceholderHook
import me.xbackpack.galaxysky.item.registry.Materials
import me.xbackpack.galaxysky.item.registry.Pickaxes
import me.xbackpack.galaxysky.item.registry.VanillaItems
import me.xbackpack.galaxysky.listener.PlayerListenerRegistry
import me.xbackpack.galaxysky.service.AFKService
import me.xbackpack.galaxysky.service.ChatService
import me.xbackpack.galaxysky.service.LocationService
import me.xbackpack.galaxysky.service.MineService
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.logging.Logger
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class GalaxySky : JavaPlugin() {
    override fun onEnable() {
        instance = this
        meta = pluginMeta
        pluginManager = server.pluginManager
        log = logger

        // Every second
        setupTimedTask({ Bukkit.getOnlinePlayers().forEach(AFKService::check) }, 1.seconds)

        // Every 30 seconds
        setupTimedTask(MineService::resetMines, 30.seconds)

        // Every 5 minutes
        setupTimedTask(ChatService::sendRandomMessage, 5.minutes)

        logger.info("Set up scheduled events!")

        // PlaceholderAPI Custom Placeholders
        PlaceholderHook.register()

        logger.info("Hooked into PlaceholderAPI!")

        // LuckPerms
        LuckPermsHook.init()

        logger.info("Hooked into LuckPerms!")

        // Items
        Materials.init()
        Pickaxes.init()
        VanillaItems.init()

        logger.info("Loaded items!")

        // Worlds
        LocationService.init()

        logger.info("Loaded worlds!")

        // Listeners
        PlayerListenerRegistry.init()
        ChatService.init()

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

    private fun setupTimedTask(
        block: () -> Unit,
        delay: Duration,
    ) {
        server.scheduler.runTaskTimer(this, Runnable(block), delay.inWholeSeconds * 20L, delay.inWholeSeconds * 20L)
    }

    companion object {
        lateinit var instance: GalaxySky
        lateinit var meta: PluginMeta
        lateinit var pluginManager: PluginManager
        lateinit var log: Logger
        var chatMuted = false

        fun createKey(key: String) = NamespacedKey(instance, key)

        fun getFile(
            directory: String,
            fileName: String,
        ) = File("${instance.dataFolder}${File.separator}$directory", fileName)
    }
}
