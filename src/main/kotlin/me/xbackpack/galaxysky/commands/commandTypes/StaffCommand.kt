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
        val targetPlayer = if (args.isNotEmpty()) Bukkit.getPlayer(args[0]) else null

        if (sender is Player) {
            if (this is CooldownCommand) {
                if (checkCooldown(sender)) return
            }

            handlePlayer(sender, targetPlayer, args)
        } else {
            handleNonPlayer(sender, targetPlayer, args)
        }
    }

    fun handlePlayer(
        player: Player,
        targetPlayer: Player?,
        args: List<String>,
    ) {
        if (isStaff(player)) {
            if (targetPlayer != null && player != targetPlayer) {
                player.sendMessage(getMessage(targetPlayer))
                func(targetPlayer, args)
            } else {
                func(player, args)
            }
        } else {
            player.sendMessage("§cYou don't have permission to use this command")
        }
    }

    fun handleNonPlayer(
        sender: CommandSender,
        targetPlayer: Player?,
        args: List<String>,
    ) {
        if (targetPlayer != null) {
            sender.sendMessage(getMessage(targetPlayer))
            func(targetPlayer, args)
        } else {
            sender.sendMessage("Player not found or not specified")
        }
    }

    fun isStaff(player: Player): Boolean =
        player.isOp || PlaceholderAPI.setPlaceholders(player, "%luckperms_has_groups_on_track_staff%") == "yes"
}
