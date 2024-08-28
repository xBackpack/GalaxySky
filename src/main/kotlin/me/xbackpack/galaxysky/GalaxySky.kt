package me.xbackpack.galaxysky

import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import me.xbackpack.galaxysky.commands.player.AFK
import me.xbackpack.galaxysky.util.commandTypes.CommandBase
import org.bukkit.plugin.java.JavaPlugin

class GalaxySky : JavaPlugin() {
    companion object {
        lateinit var instance: GalaxySky
            private set
    }

    override fun onEnable() {
        instance = this

        val manager = lifecycleManager

        manager.registerEventHandler(LifecycleEvents.COMMANDS) { event ->
            val commands = event.registrar()

            registerCommands(
                commands,
                AFK(),
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
