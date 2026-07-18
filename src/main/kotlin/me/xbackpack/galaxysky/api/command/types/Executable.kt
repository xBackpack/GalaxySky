package me.xbackpack.galaxysky.api.command.types

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.ArgumentBuilder
import io.papermc.paper.command.brigadier.CommandSourceStack
import me.xbackpack.galaxysky.api.command.Cooldown
import me.xbackpack.galaxysky.api.command.Permission
import me.xbackpack.galaxysky.api.command.Requirement
import me.xbackpack.galaxysky.api.util.ConsoleFunction
import me.xbackpack.galaxysky.api.util.PlayerFunction
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

interface Executable {
    val name: String
    val requirement: Requirement
    val permission: Permission?
    val parent: Branch?

    val playerFunctions: MutableList<PlayerFunction>
    val consoleFunctions: MutableList<ConsoleFunction>

    fun doForPlayer(block: (Player, List<Argument>) -> Unit) {
        playerFunctions += block
    }

    fun doForConsole(block: (ConsoleCommandSender, List<Argument>) -> Unit) {
        consoleFunctions += block
    }

    fun <T : ArgumentBuilder<CommandSourceStack, *>> T.buildTo(
        cooldown: Cooldown?,
        args: List<Argument>,
    ): T =
        apply {
            requires { src -> this@Executable.requirement.predicate(src.sender) || permission?.accept(src.sender) == true }
            executes { ctx ->
                val sender = ctx.source.sender

                val saturatedArgs = args.onEach { it.ctx = ctx }

                cooldown
                    ?.takeIf { sender is Player }
                    ?.takeIf { it.isOnCooldown(sender as Player) }
                    ?.let {
                        it.sendMessage(sender as Player)
                        return@executes Command.SINGLE_SUCCESS
                    }

                when (sender) {
                    is Player -> {
                        playerFunctions.forEach { it(sender, saturatedArgs) }
                    }

                    is ConsoleCommandSender -> {
                        consoleFunctions.forEach { it(sender, saturatedArgs) }
                    }
                }

                cooldown
                    ?.takeIf { sender is Player }
                    ?.startCooldown(sender as Player)

                Command.SINGLE_SUCCESS
            }
        }
}
