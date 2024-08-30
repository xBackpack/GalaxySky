package me.xbackpack.galaxysky

import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import me.xbackpack.galaxysky.commands.player.AFKCmd
import me.xbackpack.galaxysky.commands.player.ApplyCmd
import me.xbackpack.galaxysky.commands.player.ColoursCmd
import me.xbackpack.galaxysky.commands.player.DropsCmd
import me.xbackpack.galaxysky.commands.player.IPCmd
import me.xbackpack.galaxysky.commands.player.PVPCmd
import me.xbackpack.galaxysky.commands.player.PlaytimeCmd
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
                AFKCmd(),
                ApplyCmd(),
                ColoursCmd(),
                DropsCmd(),
                IPCmd(),
                PlaytimeCmd(),
                PVPCmd(),
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
