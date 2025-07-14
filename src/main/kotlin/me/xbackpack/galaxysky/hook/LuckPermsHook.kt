package me.xbackpack.galaxysky.hook

import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.event.node.NodeAddEvent
import net.luckperms.api.model.user.User
import org.bukkit.Bukkit

object LuckPermsHook {
    fun init() {
        val bus = LuckPermsProvider.get().eventBus

        bus.subscribe(NodeAddEvent::class.java) { event ->
            if (event.isUser) {
                val user = event.target as User

                Bukkit.getPlayer(user.uniqueId)?.updateCommands()
            }
        }
    }
}
