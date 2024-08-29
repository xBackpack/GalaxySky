package me.xbackpack.galaxysky

import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import me.xbackpack.galaxysky.commands.player.AFK
import me.xbackpack.galaxysky.commands.player.Apply
import me.xbackpack.galaxysky.commands.player.Colours
import me.xbackpack.galaxysky.commands.player.Drops
import me.xbackpack.galaxysky.commands.player.IP
import me.xbackpack.galaxysky.commands.player.Playtime
import me.xbackpack.galaxysky.util.commandTypes.CommandBase
import org.bukkit.plugin.java.JavaPlugin

class GalaxySky : JavaPlugin() {
    companion object {
        lateinit var instance: GalaxySky
            private set
        lateinit var version: String
    }

    override fun onEnable() {
        instance = this
        version = pluginMeta.version

        Placeholders().register()

        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { event ->
            val commands = event.registrar()

            registerCommands(
                commands,
                AFK(),
                Apply(),
                Colours(),
                Drops(),
                IP(),
                Playtime(),
            )
        }

        logger.info("Loaded!")
    }

    private fun registerCommands(
        registrar: Commands,
        vararg commands: CommandBase,
    ) {
        for (command in commands) {
            registrar.register(command.commandName, command.description, command.aliases, command)
        }
    }
}
