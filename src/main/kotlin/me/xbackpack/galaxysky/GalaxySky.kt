package me.xbackpack.galaxysky

import io.papermc.paper.plugin.configuration.PluginMeta
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.api.message.MessageBuilder
import me.xbackpack.galaxysky.hook.LuckPermsHook
import me.xbackpack.galaxysky.hook.PlaceholderHook
import me.xbackpack.galaxysky.registry.command.CommandRegistry
import me.xbackpack.galaxysky.service.AFKService
import me.xbackpack.galaxysky.service.ChatService
import me.xbackpack.galaxysky.service.ListenerService
import me.xbackpack.galaxysky.service.MiningService
import me.xbackpack.galaxysky.service.PlayerService
import me.xbackpack.galaxysky.service.ScoreboardService
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.inventory.InventoryHolder
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class GalaxySky : JavaPlugin() {
    override fun onEnable() {
        instance = this
        meta = pluginMeta
        pluginManager = server.pluginManager
        Companion.logger = logger

        // PlaceholderAPI
        PlaceholderHook.register()

        logger.info("Hooked into PlaceholderAPI!")

        // LuckPerms
        LuckPermsHook.init()

        logger.info("Hooked into LuckPerms!")

        // Listeners
        ListenerService.init()
        ChatService.init()

        logger.info("Loaded listeners!")

        // Commands
        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { event ->
            val registrar = event.registrar()

            CommandRegistry.commands.forEach { command ->
                registrar.register(command.build().build(), command.description, command.aliases)
            }
        }

        logger.info("Loaded commands!")

        // Every second
        setupTimedTask(false, 1.seconds, AFKService::update, PlayerService::updatePlaytime)

        // Every 5 seconds
        setupTimedTask(false, 5.seconds, ScoreboardService::updateAll)

        // Every 30 seconds
        setupTimedTask(true, 30.seconds, MiningService::resetMines)

        // Every 5 minutes
        setupTimedTask(false, 5.minutes, ChatService::sendRandomMessage)

        logger.info("Set up scheduled events!")

        logger.info("Enabled!")
    }

    private fun setupTimedTask(
        startInstantly: Boolean,
        delay: Duration,
        vararg blocks: () -> Unit,
    ) {
        runTaskTimer(startInstantly, delay) {
            blocks.forEach { it() }
        }
    }

    companion object {
        lateinit var instance: GalaxySky
        lateinit var meta: PluginMeta
        lateinit var pluginManager: PluginManager
        lateinit var logger: Logger
        var chatMuted = false

        fun runTaskLater(
            delay: Duration,
            block: () -> Unit,
        ) = instance.server.scheduler.runTaskLater(
            instance,
            Runnable { block() },
            delay.inWholeTicks,
        )

        fun runTaskTimer(
            startInstantly: Boolean,
            timer: Duration,
            block: () -> Unit,
        ) = instance.server.scheduler.runTaskTimer(
            instance,
            Runnable { block() },
            if (startInstantly) 0 else timer.inWholeTicks,
            timer.inWholeTicks,
        )

        fun createInventory(
            holder: InventoryHolder,
            rows: Int,
            builder: MessageBuilder.() -> Unit,
        ) = Bukkit.createInventory(holder, rows * 9, Message.create(builder).component)

        fun createKey(key: String) = NamespacedKey(instance, key)

//        fun getFile(
//            directory: String,
//            fileName: String,
//        ) = File("${instance.dataFolder}${File.separator}$directory", fileName)
    }
}
