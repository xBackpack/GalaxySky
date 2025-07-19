package me.xbackpack.galaxysky.service

import me.clip.placeholderapi.PlaceholderAPI
import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.node.types.PermissionNode
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

object PermissionService {
    fun hasPermission(
        player: Player,
        permission: String,
    ): Boolean {
        val userManager = LuckPermsProvider.get().userManager
        val user = userManager.getUser(player.uniqueId) ?: return false

        return user
            .cachedData
            .permissionData
            .checkPermission(permission)
            .asBoolean()
    }

    fun givePermission(
        player: Player,
        permission: String,
    ) {
        val userManager = LuckPermsProvider.get().userManager
        val user = userManager.getUser(player.uniqueId) ?: return
        val data = user.data()

        data.add(PermissionNode.builder(permission).build())

        player.updateCommands()

        userManager.saveUser(user)
    }

    fun isStaff(player: OfflinePlayer) = PlaceholderAPI.setPlaceholders(player, "%luckperms_has_groups_on_track_staff%") == "yes"

    fun isInGroup(
        player: Player,
        groupName: String,
    ) = PlaceholderAPI.setPlaceholders(player, "%luckperms_primary_group_name%") == groupName
}
