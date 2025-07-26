package me.xbackpack.galaxysky.command.node

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.ArgumentBuilder
import io.papermc.paper.command.brigadier.CommandSourceStack
import me.xbackpack.galaxysky.command.common.Argument
import me.xbackpack.galaxysky.command.common.CommandDsl
import me.xbackpack.galaxysky.enum.command.SenderRequirement
import me.xbackpack.galaxysky.service.LuckPermsService
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandDsl
class NodeBuilder<T : ArgumentBuilder<CommandSourceStack, T>>(
    root: T,
    node: Node,
    block: (CommandSender, List<Argument>) -> Unit,
) {
    var final = root

    init {
        node.subcommands.forEach {
            final = final.then(it.build())
        }

        node.optionals.forEach {
            final = final.then(it.build())
        }

        final =
            final.executes { ctx ->
                val src = ctx.source
                val sender = src.sender

                if (node.requirement == SenderRequirement.PLAYER && src.executor !is Player) {
                    sender.sendMessage("Only players can use this comand")
                    return@executes Command.SINGLE_SUCCESS
                }

                node.cooldown
                    ?.takeIf { sender is Player }
                    ?.takeIf { it.isOnCooldown(sender as Player) }
                    ?.let {
                        it.sendMessage(sender as Player)
                        return@executes Command.SINGLE_SUCCESS
                    }

                val finalArgs = node.arguments.map { Argument(it.name) }

                block(sender, finalArgs)

                node.cooldown
                    ?.takeIf { sender is Player }
                    ?.startCooldown(sender as Player)

                Command.SINGLE_SUCCESS
            }

        when (node.requirement) {
            SenderRequirement.STAFF -> {
                final =
                    final.requires { src ->
                        val sender = src.sender

                        sender is Player && (sender.isOp || LuckPermsService.isStaff(sender))
                    }
            }
            SenderRequirement.STAFF_OR_CONSOLE -> {
                final =
                    final.requires { src ->
                        val sender = src.sender

                        sender.isOp || sender is Player && LuckPermsService.isStaff(sender)
                    }
            }
            else -> null
        }

        node.permission?.let {
            final =
                final.requires { src ->
                    val sender = src.sender

                    sender.isOp || sender.hasPermission(it)
                }
        }
    }
}
