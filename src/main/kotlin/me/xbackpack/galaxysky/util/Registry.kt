package me.xbackpack.galaxysky.util

import me.xbackpack.galaxysky.GalaxySky
import me.xbackpack.galaxysky.command.api.Command
import me.xbackpack.galaxysky.command.api.CommandBuilder
import me.xbackpack.galaxysky.command.api.CommandFunction
import me.xbackpack.galaxysky.command.api.util.UserCooldown
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

interface Registry<T> {
    fun init(): List<T>

    companion object {
        fun buildCommand(
            name: String,
            description: String,
            aliases: List<String>,
            cooldown: UserCooldown?,
            block: CommandFunction,
        ) = Command(
            CommandBuilder(name, block, cooldown),
            description,
            aliases,
            cooldown,
        )

        inline fun <reified T : Event> hookEvent(
            priority: EventPriority = EventPriority.NORMAL,
            ignoreCancelled: Boolean = false,
            crossinline handler: (T) -> Unit,
        ) {
            val listener = object : Listener {}

            val plugin = GalaxySky.instance

            plugin.server.pluginManager.registerEvent(
                T::class.java,
                listener,
                priority,
                { _, event ->
                    if (event is T) {
                        handler(event)
                    }
                },
                GalaxySky.instance,
                ignoreCancelled,
            )
        }
    }
}
