package me.xbackpack.galaxysky

import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import me.xbackpack.galaxysky.commands.commandTypes.CommandBase
import me.xbackpack.galaxysky.commands.commandTypes.ListenerCommand
import me.xbackpack.galaxysky.commands.player.AFKCmd
import me.xbackpack.galaxysky.commands.player.AetherCmd
import me.xbackpack.galaxysky.commands.player.ApplyCmd
import me.xbackpack.galaxysky.commands.player.ColoursCmd
import me.xbackpack.galaxysky.commands.player.DropsCmd
import me.xbackpack.galaxysky.commands.player.EndCmd
import me.xbackpack.galaxysky.commands.player.IPCmd
import me.xbackpack.galaxysky.commands.player.NetherCmd
import me.xbackpack.galaxysky.commands.player.PVPCmd
import me.xbackpack.galaxysky.commands.player.PlaytimeCmd
import me.xbackpack.galaxysky.commands.player.RedeemCmd
import me.xbackpack.galaxysky.commands.player.ShopCmd
import me.xbackpack.galaxysky.commands.player.SitCmd
import me.xbackpack.galaxysky.commands.player.SpawnCmd
import me.xbackpack.galaxysky.commands.player.StartCmd
import me.xbackpack.galaxysky.commands.player.TrashCmd
import me.xbackpack.galaxysky.commands.rank.CompressCmd
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
                // Player Commands
                AetherCmd(),
                AFKCmd(),
                ApplyCmd(),
                ColoursCmd(),
                DropsCmd(),
                EndCmd(),
                IPCmd(),
                NetherCmd(),
                PlaytimeCmd(),
                PVPCmd(),
                RedeemCmd(),
                ShopCmd(),
                SitCmd(),
                SpawnCmd(),
                StartCmd(),
                TrashCmd(),
                // Rank Commands
                CompressCmd(),
            )
        }

        logger.info("Loaded!")
    }

    private fun registerCommands(
        registrar: Commands,
        vararg commands: CommandBase,
    ) {
        commands.forEach {
            registrar.register(it.commandName, it.description, it.aliases, it)
            (it as? ListenerCommand)?.registerListener()
        }
    }
}
