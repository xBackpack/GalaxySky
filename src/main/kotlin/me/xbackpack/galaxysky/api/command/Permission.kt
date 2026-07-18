package me.xbackpack.galaxysky.api.command

import me.xbackpack.galaxysky.service.LuckPermsService.checkPermission
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@JvmInline
value class Permission(
    private val permission: String,
) {
    fun accept(sender: CommandSender) = sender is Player && sender.checkPermission(permission)
}
