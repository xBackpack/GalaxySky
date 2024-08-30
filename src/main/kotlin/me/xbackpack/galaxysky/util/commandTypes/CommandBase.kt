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
        val interfaces = this::class.java.interfaces

        if (requiresPlayer) {
            val player = sender as Player

            if (interfaces.contains(TeleportCommand::class.java)) {
                player.teleport((this as TeleportCommand).location(player))
            }

            if (interfaces.contains(CooldownCommand::class.java)) {
                (this as CooldownCommand).startCooldown(player)
            }

            if (interfaces.contains(PlayerMessageCommand::class.java)) {
                player.sendMessage((this as PlayerMessageCommand).message(player))
            }

            if (interfaces.contains(ToggleableCommand::class.java)) {
                (this as ToggleableCommand).toggleFeature(player)
            }
        }

        if (interfaces.contains(ListenerCommand::class.java)) {
            (this as ListenerCommand).registerListener()
        }

        if (interfaces.contains(MessageCommand::class.java)) {
            sender.sendMessage((this as MessageCommand).message)
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
