package me.xbackpack.galaxysky.commands.rank

import me.xbackpack.galaxysky.commands.commandTypes.CooldownCommand
import me.xbackpack.galaxysky.commands.commandTypes.StaffCommand
import me.xbackpack.galaxysky.util.PluginPermission
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.UUID

class CompressCmd :
    StaffCommand,
    CooldownCommand {
    override val commandName = "compress"
    override val description = "Compresses ores in the player's inventory"
    override val permission = PluginPermission("galaxysky.command.compress")

    override val playersWithCooldown = hashSetOf<UUID>()
    override val cooldownDuration = 200L

    override fun getMessage(player: Player) =
        Component
            .text("You compressed ${player.name}'s inventory!")
            .color(NamedTextColor.GREEN)

    override fun func(
        player: Player,
        args: List<String>,
    ) {
        if (args.isEmpty()) {
            player.sendMessage(Component.text("You need to specify a material to compress").color(NamedTextColor.RED))
            return
        }
        compressInvOfPlayer(player, args[0])
    }

    override fun tabComplete(
        sender: CommandSender,
        args: Array<String>,
    ): List<String> {
        val collection = arrayListOf<String>()

        if (args.size > 1) {
            collection.addAll(
                arrayListOf(
                    "cobblestone",
                    "coal",
                    "iron",
                    "copper",
                    "gold",
                    "diamond",
                    "quartz",
                    "obsidian",
                    "netherite_scrap",
                    "magma",
                    "redstone",
                    "netherite",
                    "ruby",
                    "purpur",
                    "lapis",
                    "charged_stone",
                    "prismarine",
                    "opal",
                ),
            )
        }

        return collection
    }

    // RARE

    override fun handlePlayer(
        player: Player,
        targetPlayer: Player?,
        args: List<String>,
    ) {
        val isStaff = isStaff(player)

        if (args.isEmpty()) {
            player.sendMessage("§cYou need to specify a material to compress")
            return
        }

        if (isStaff) {
            if (args.size > 1) {
                targetPlayer?.let {
                    if (player != it) {
                        player.sendMessage("§aYou compressed ${it.name}'s inventory!")
                    }
                    compressInvOfPlayer(it, args[1])
                } ?: player.sendMessage("§cPlayer not found")
            } else {
                compressInvOfPlayer(player, args[0])
            }
        } else {
            if (checkCooldown(player)) {
                player.sendMessage(cooldownMessage)
            } else {
                startCooldown(player)
                compressInvOfPlayer(player, args[0])
            }
        }
    }

    override fun handleNonPlayer(
        sender: CommandSender,
        targetPlayer: Player?,
        args: List<String>,
    ) {
        if (args.isNotEmpty()) {
            if (args.size < 2) {
                sender.sendMessage("§cYou need to specify a material to compress")
                return
            }

            targetPlayer?.let {
                sender.sendMessage("§aYou compressed ${it.name}'s inventory!")
                compressInvOfPlayer(it, args[1])
            } ?: sender.sendMessage("§cPlayer not found")
        } else {
            sender.sendMessage("§cYou need to specify a player")
        }
    }

    private fun compressInvOfPlayer(
        player: Player,
        material: String,
    ) {
        // CompressorHandler.compressInv(player, material.uppercase())
        player.sendMessage("§aYour inventory was compressed!")
    }
}
