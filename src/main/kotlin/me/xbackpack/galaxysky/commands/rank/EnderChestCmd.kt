package me.xbackpack.galaxysky.commands.rank

import me.xbackpack.galaxysky.commands.commandTypes.StaffCommand
import me.xbackpack.galaxysky.util.PluginPermission
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player

class EnderChestCmd : StaffCommand {
    override val commandName = "enderchest"
    override val description = "Opens the player's ender chest"
    override val aliases = arrayListOf("ec", "echest")
    override val permission = PluginPermission("galaxysky.command.enderchest")

    override fun getMessage(player: Player) = Component.text("You compressed ${player.name}'s inventory!").color(NamedTextColor.GREEN)

    override fun func(
        player: Player,
        args: List<String>,
    ) {
        player.openInventory(player.enderChest)
    }
}
