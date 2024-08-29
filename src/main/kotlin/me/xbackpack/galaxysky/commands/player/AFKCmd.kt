package me.xbackpack.galaxysky.commands.player

import me.xbackpack.galaxysky.util.Worlds
import me.xbackpack.galaxysky.util.commandTypes.TeleportCommand
import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AFKCmd : TeleportCommand {
    override val commandName = "afk"
    override val description = "Teleports the player to the AFK area"

    override fun location(player: Player) = Location(Worlds.world, -28.5, 102.0, 10.5, 90f, 0f)

    override fun executeCommand(
        sender: CommandSender,
        args: List<String>,
    ) {
        teleport(sender as Player)
    }
}
