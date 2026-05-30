package me.xbackpack.galaxysky.api.command

import org.bukkit.entity.Player

@JvmInline
value class Permission(
    private val permission: String,
) {
    fun accept(player: Player) = player.hasPermission(permission)
}
