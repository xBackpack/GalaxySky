package me.xbackpack.galaxysky

import io.papermc.paper.plugin.configuration.PluginMeta
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import me.xbackpack.galaxysky.command.registry.SuperCommandRegistry
import me.xbackpack.galaxysky.hook.LuckPermsHook
import me.xbackpack.galaxysky.hook.PlaceholderHook
import me.xbackpack.galaxysky.listener.PlayerListenerRegistry
import me.xbackpack.galaxysky.service.AFKService
import me.xbackpack.galaxysky.service.ChatService
import me.xbackpack.galaxysky.service.MineService
import me.xbackpack.galaxysky.service.ScoreboardService
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

        // PlaceholderAPI Custom Placeholders
        PlaceholderHook.register()

        logger.info("Hooked into PlaceholderAPI!")

        // LuckPerms
        LuckPermsHook.init()

        logger.info("Hooked into LuckPerms!")

        // Scoreboard
        ScoreboardService.setupScoreboard()

        logger.info("Setup Scoreboard!")

        // Listeners
        PlayerListenerRegistry.init()
        ChatService.init()

        logger.info("Loaded listeners!")

        // Commands
        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { event ->
            val registrar = event.registrar()

            SuperCommandRegistry.commands.forEach { command ->
                registrar.register(command.build().build(), command.description, command.aliases)
            }
        }

        logger.info("Loaded commands!")

        // Every second
        setupTimedTask(1.seconds, AFKService::update, ScoreboardService::update)

        // Every 30 seconds
        setupTimedTask(30.seconds, MineService::resetMines)

        // Every 5 minutes
        setupTimedTask(5.minutes, ChatService::sendRandomMessage)

        logger.info("Set up scheduled events!")

        logger.info("Enabled!")
    }

    private fun setupTimedTask(
        delay: Duration,
        vararg blocks: () -> Unit,
    ) {
        server.scheduler.runTaskTimer(
            this,
            Runnable {
                blocks.forEach { it() }
            },
            delay.inWholeSeconds * 20L,
            delay.inWholeSeconds * 20L,
        )
    }

    companion object {
        lateinit var instance: GalaxySky
        lateinit var meta: PluginMeta
        lateinit var pluginManager: PluginManager
        lateinit var log: Logger
        var chatMuted = false

        fun runTaskLater(
            delay: Duration,
            block: () -> Unit,
        ) {
            instance.server.scheduler.runTaskLater(
                instance,
                Runnable { block() },
                delay.inWholeSeconds * 20L,
            )
        }

        fun createKey(key: String) = NamespacedKey(instance, key)

        fun getFile(
            directory: String,
            fileName: String,
        ) = File("${instance.dataFolder}${File.separator}$directory", fileName)
    }
}
