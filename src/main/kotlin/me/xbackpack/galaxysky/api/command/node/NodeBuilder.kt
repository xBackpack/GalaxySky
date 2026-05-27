package me.xbackpack.galaxysky.api.command.node

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.ArgumentBuilder
import io.papermc.paper.command.brigadier.CommandSourceStack
import me.xbackpack.galaxysky.api.command.common.ArgumentGetter
import me.xbackpack.galaxysky.api.command.common.CommandDsl
import me.xbackpack.galaxysky.api.command.function.CommandFunction
import me.xbackpack.galaxysky.enum.command.SenderRequirement
import me.xbackpack.galaxysky.service.LuckPermsService
import org.bukkit.command.CommandSender
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

                // Entity executing command checks
                when (node.requirement.type) {
                    SenderRequirement.PLAYER,
                    SenderRequirement.PERMISSION,
                    SenderRequirement.STAFF,
                    SenderRequirement.STAFF_OR_PERMISSION,
                    -> {
                        if (src.executor !is Player) {
                            sender.sendMessage("Only players can use this comand")
                            return@executes Command.SINGLE_SUCCESS
                        }
                    }

                    SenderRequirement.CONSOLE -> {
                        if (src.executor !is ConsoleCommandSender) {
                            sender.sendMessage("Only console can use this command")
                            return@executes Command.SINGLE_SUCCESS
                        }
                    }

                    SenderRequirement.STAFF_OR_CONSOLE,
                    SenderRequirement.STAFF_OR_PERMISSION_OR_CONSOLE,
                    -> {
                        // No checks
                    }
                }

                node.cooldown
                    ?.takeIf { sender is Player }
                    ?.takeIf { it.isOnCooldown(sender as Player) }
                    ?.let {
                        it.sendMessage(sender as Player)
                        return@executes Command.SINGLE_SUCCESS
                    }

                val getter = ArgumentGetter(ctx)

                // Command functionality checks
                when (node.requirement.type) {
                    SenderRequirement.PLAYER,
                    SenderRequirement.PERMISSION,
                    SenderRequirement.STAFF,
                    SenderRequirement.STAFF_OR_PERMISSION,
                    -> {
                        node.playerFunction?.runWith(sender as Player, getter)
                    }

                    SenderRequirement.STAFF_OR_CONSOLE,
                    SenderRequirement.STAFF_OR_PERMISSION_OR_CONSOLE,
                    -> {
                        (sender as? Player)?.let { player ->
                            node.playerFunction?.runWith(player, getter)
                        }

                        (sender as? ConsoleCommandSender)?.let { console ->
                            node.consoleFunction?.runWith(console, getter)
                        }
                    }

                    SenderRequirement.CONSOLE -> {
                        node.consoleFunction?.runWith(sender as ConsoleCommandSender, getter)
                    }
                }

                node.cooldown
                    ?.takeIf { sender is Player }
                    ?.startCooldown(sender as Player)

                Command.SINGLE_SUCCESS
            }

        when (node.requirement.type) {
            SenderRequirement.PLAYER, SenderRequirement.CONSOLE -> {
                // Already done in final.executes {}
            }

            SenderRequirement.PERMISSION -> {
                final =
                    final.requires { src ->
                        val sender = src.sender

                        sender is Player && (sender.isOp || LuckPermsService.hasPermission(sender, node.requirement.permission!!))
                    }
            }

            SenderRequirement.STAFF -> {
                final =
                    final.requires { src ->
                        val sender = src.sender

                        sender is Player && (sender.isOp || LuckPermsService.isStaff(sender))
                    }
            }

            SenderRequirement.STAFF_OR_PERMISSION -> {
                final =
                    final.requires { src ->
                        val sender = src.sender

                        sender is Player &&
                            (
                                sender.isOp ||
                                    LuckPermsService.isStaff(sender) ||
                                    LuckPermsService.hasPermission(sender, node.requirement.permission!!)
                            )
                    }
            }

            SenderRequirement.STAFF_OR_PERMISSION_OR_CONSOLE -> {
                final =
                    final.requires { src ->
                        val sender = src.sender

                        sender.isOp ||
                            (
                                sender is Player && (
                                    LuckPermsService.isStaff(sender) ||
                                        LuckPermsService.hasPermission(sender, node.requirement.permission!!)
                                )
                            )
                    }
            }

            SenderRequirement.STAFF_OR_CONSOLE -> {
                final =
                    final.requires { src ->
                        val sender = src.sender

                        sender.isOp || (sender is Player && LuckPermsService.isStaff(sender))
                    }
            }
        }
    }

    private fun <T : CommandSender, R : CommandFunction> ((T, ArgumentGetter) -> R).runWith(
        sender: T,
        getter: ArgumentGetter,
    ) = invoke(sender, getter).runAll()
}
