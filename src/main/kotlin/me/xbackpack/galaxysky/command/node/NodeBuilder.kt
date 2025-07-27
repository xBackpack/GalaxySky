package me.xbackpack.galaxysky.command.node

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.ArgumentBuilder
import io.papermc.paper.command.brigadier.CommandSourceStack
import me.xbackpack.galaxysky.command.common.ArgumentGetter
import me.xbackpack.galaxysky.command.common.CommandDsl
import me.xbackpack.galaxysky.enum.command.SenderRequirement
import me.xbackpack.galaxysky.service.LuckPermsService
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

@CommandDsl
class NodeBuilder<T : ArgumentBuilder<CommandSourceStack, T>>(
    root: T,
    node: Node,
) {
    var final = root

    init {
        node.subcommands.forEach {
            final = final.then(it.build())
        }

        node.optionals.forEach {
            final = final.then(it.build())
        }

        node.arguments.forEach {
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

                val getter = ArgumentGetter(ctx)

                when (node.requirement) {
                    SenderRequirement.PLAYER -> {
                        val player = sender as Player
                        node.playerFunction(player, getter)?.runAll()
                    }
                    SenderRequirement.STAFF -> {
                        val player = sender as Player
                        node.staffFunction(player, getter)?.runAll()
                    }
                    SenderRequirement.STAFF_OR_CONSOLE -> {
                        (sender as? Player)?.let { player ->
                            node.staffFunction(player, getter)?.runAll()
                        }

                        (sender as? ConsoleCommandSender)?.let { console ->
                            node.consoleFunction(console, getter)?.runAll()
                        }
                    }
                    SenderRequirement.ANY -> {
                        (sender as? Player)?.let { player ->
                            if (LuckPermsService.isStaff(player)) {
                                node.staffFunction(player, getter)?.runAll()
                            } else {
                                node.playerFunction(player, getter)?.runAll()
                            }
                        }

                        (sender as? ConsoleCommandSender)?.let { console ->
                            node.consoleFunction(console, getter)?.runAll()
                        }
                    }
                    else -> null
                }

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
