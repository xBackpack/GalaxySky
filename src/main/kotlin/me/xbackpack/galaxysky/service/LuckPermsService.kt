package me.xbackpack.galaxysky.service

import net.kyori.adventure.text.format.NamedTextColor
import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.model.user.User
import net.luckperms.api.node.types.PermissionNode
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

object LuckPermsService {
    private fun OfflinePlayer.getUser() = LuckPermsProvider.get().userManager.getUser(uniqueId) ?: error("Could not find player: $name")

    private fun User.saveUser() = LuckPermsProvider.get().userManager.saveUser(this)

    fun OfflinePlayer.hasPermission(permission: String) =
        getUser()
            .cachedData.permissionData
            .checkPermission(permission)
            .asBoolean()

    fun OfflinePlayer.givePermission(permission: String) {
        val user = getUser()
        val data = user.data()

        data.add(PermissionNode.builder(permission).build())

        if (isOnline) {
            (this as Player).updateCommands()
        }

        user.saveUser()
    }

    fun OfflinePlayer.getPrimaryGroupName() = getUser().primaryGroup

    fun OfflinePlayer.getPrefix() =
        getUser().cachedData.metaData.prefix
            ?: getGroup(getPrimaryGroupName()).cachedData.metaData.prefix
            ?: ""

    fun OfflinePlayer.getSuffix() =
        getUser().cachedData.metaData.suffix
            ?: getGroup(getPrimaryGroupName()).cachedData.metaData.suffix
            ?: ""

    fun OfflinePlayer.isStaff() = getTrack("staff").containsGroup(getPrimaryGroupName())

    private fun getGroup(name: String) = LuckPermsProvider.get().groupManager.getGroup(name) ?: error("Could not find group: $name}")

    private fun getTrack(name: String) = LuckPermsProvider.get().trackManager.getTrack(name) ?: error("Could not find track: $name")

    fun getNameColour(groupName: String): NamedTextColor =
        when (groupName) {
            "owner" -> NamedTextColor.DARK_RED
            "dev" -> NamedTextColor.GOLD
            "manager" -> NamedTextColor.BLUE
            "admin" -> NamedTextColor.RED
            "mod" -> NamedTextColor.GREEN
            "helper" -> NamedTextColor.DARK_GREEN
            "galactic" -> NamedTextColor.DARK_PURPLE
            "immortal" -> NamedTextColor.DARK_AQUA
            "mvp" -> NamedTextColor.AQUA
            "vip" -> NamedTextColor.YELLOW
            else -> NamedTextColor.GRAY
        }
}
