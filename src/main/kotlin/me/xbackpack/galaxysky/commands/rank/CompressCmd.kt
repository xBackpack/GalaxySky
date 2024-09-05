package me.xbackpack.galaxysky.commands.rank

import me.xbackpack.galaxysky.commands.commandTypes.StaffCommand
import me.xbackpack.galaxysky.util.PluginPermission
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player

class CompressCmd : StaffCommand {
    override val commandName = "compress"
    override val description = "Compresses ores in the player's inventory"
    override val permission = PluginPermission("galaxysky.command.compress")

    override fun getMessage(player: Player) = Component.text("You compressed ${player.name}'s inventory!").color(NamedTextColor.GREEN)

    override fun func(
        player: Player,
        args: List<String>,
    ) = TODO("FINISH")
}
