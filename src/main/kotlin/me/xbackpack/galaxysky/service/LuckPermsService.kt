package me.xbackpack.galaxysky.service

import net.kyori.adventure.text.format.NamedTextColor
import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.model.user.User
import net.luckperms.api.node.types.PermissionNode
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

object LuckPermsService {
    private val luckPerms = LuckPermsProvider.get()

    private fun getUser(player: OfflinePlayer) =
        luckPerms.userManager.getUser(player.uniqueId) ?: error("Could not find player: ${player.name}")

    private fun getGroup(name: String) = luckPerms.groupManager.getGroup(name) ?: error("Could not find group: $name}")

    private fun getTrack(name: String) = luckPerms.trackManager.getTrack(name) ?: error("Could not find track: $name")

    private fun saveUser(user: User) = luckPerms.userManager.saveUser(user)

    fun hasPermission(
        player: OfflinePlayer,
        permission: String,
    ) = getUser(player)
        .cachedData.permissionData
        .checkPermission(permission)
        .asBoolean()

    fun givePermission(
        player: OfflinePlayer,
        permission: String,
    ) {
        val user = getUser(player)
        val data = user.data()

        data.add(PermissionNode.builder(permission).build())

        player
            .takeIf { it.isOnline }
            ?.let { (player as Player).updateCommands() }

        saveUser(user)
    }

    fun getPrimaryGroupName(player: OfflinePlayer) = getUser(player).primaryGroup

    fun getPrefix(player: OfflinePlayer) =
        getUser(player).cachedData.metaData.prefix
            ?: getGroup(getPrimaryGroupName(player)).cachedData.metaData.prefix
            ?: ""

    fun getSuffix(player: OfflinePlayer) =
        getUser(player).cachedData.metaData.suffix
            ?: getGroup(getPrimaryGroupName(player)).cachedData.metaData.suffix
            ?: ""

    fun isStaff(player: OfflinePlayer) = getTrack("staff").containsGroup(getPrimaryGroupName(player))

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
