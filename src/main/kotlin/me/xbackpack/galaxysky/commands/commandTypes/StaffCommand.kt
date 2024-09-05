package me.xbackpack.galaxysky.commands.commandTypes

import me.clip.placeholderapi.PlaceholderAPI
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

interface StaffCommand : CommandBase {
    fun getMessage(player: Player): Component

    fun func(
        player: Player,
        args: List<String>,
    )

    fun handle(
        sender: CommandSender,
        args: List<String>,
    ) {
        when (sender) {
            is Player -> handlePlayer(sender, args)
            else -> handleNonPlayer(sender, args)
        }
    }

    private fun handlePlayer(
        player: Player,
        args: List<String>,
    ) {
        val isStaff = PlaceholderAPI.setPlaceholders(player, "%luckperms_has_groups_on_track_staff%") == "yes" || player.isOp

        if (isStaff && args.isNotEmpty()) {
            Bukkit.getPlayer(args[0])?.let {
                if (player != it) player.sendMessage(getMessage(it))
                func(it, args)
            } ?: player.sendMessage("§cPlayer not found")
        } else {
            func(player, args)
        }
    }

    private fun handleNonPlayer(
        sender: CommandSender,
        args: List<String>,
    ) {
        if (args.isNotEmpty()) {
            Bukkit.getPlayer(args[0])?.let {
                sender.sendMessage(getMessage(it))
                func(it, args)
            } ?: sender.sendMessage("Player not found")
        } else {
            sender.sendMessage("You need to specify a player")
        }
    }
}
