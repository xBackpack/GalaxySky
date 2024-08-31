package me.xbackpack.galaxysky.util.commandTypes

import io.papermc.paper.command.brigadier.BasicCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

interface CommandBase : BasicCommand {
    val requiresPlayer: Boolean
        get() = false

    val commandName: String
    val description: String
    val aliases: List<String>
        get() = emptyList()

    fun command(
        sender: CommandSender,
        args: List<String>,
    ) {
        if (requiresPlayer) {
            val player = sender as Player

            when (this) {
                is TeleportCommand -> teleport(player)
                is CooldownCommand -> startCooldown(player)
                is PlayerMessageCommand -> sendMessage(player)
                is ToggleableCommand -> toggleFeature(player)
            }
        }

        when (this) {
            is ListenerCommand -> registerListener()
            is MessageCommand -> sendMessage(sender)
        }
    }

    override fun suggest(
        cmd: CommandSourceStack,
        args: Array<String>,
    ): List<String> = emptyList()

    override fun execute(
        cmd: CommandSourceStack,
        args: Array<String>,
    ) {
        val sender = cmd.sender

        if (requiresPlayer && sender !is Player) {
            sender.sendMessage("Only players can execute this command")
            return
        }

        command(sender, args.toList())
    }
}
