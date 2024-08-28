package me.xbackpack.galaxysky

import io.papermc.paper.command.brigadier.BasicCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

interface CommandBase : BasicCommand {
    val requiresPlayer: Boolean
        get() = false

    fun command(
        sender: CommandSender,
        args: Array<String>,
    )

    fun tabComplete(
        sender: CommandSender,
        args: Array<String>,
    ): List<String> = emptyList()

    override fun suggest(
        cmd: CommandSourceStack,
        args: Array<String>,
    ): List<String> = tabComplete(cmd.sender, args)

    override fun execute(
        cmd: CommandSourceStack,
        args: Array<String>,
    ) {
        val sender = cmd.sender

        if (requiresPlayer && sender !is Player) {
            sender.sendMessage("Only players can execute this command")
            return
        }

        command(sender, args)
    }
}
