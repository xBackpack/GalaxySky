package me.xbackpack.galaxysky.command.api

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver
import io.papermc.paper.command.brigadier.argument.resolvers.selector.SelectorArgumentResolver
import me.xbackpack.galaxysky.command.util.UserCooldown
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import kotlin.jvm.java

@CommandDsl
abstract class AbstractCommandNode<T : ArgumentBuilder<CommandSourceStack, T>>(
    final override var root: T,
    override val block: (CommandSender, List<CommandArgument>) -> Unit,
    override val cooldown: UserCooldown?,
) : CommandNode<T> {
    override var playerOnly = false
    protected val args = mutableListOf<Pair<String, Class<*>>>()

    init {
        root =
            root.executes { ctx ->
                val source = ctx.source
                val sender = source.sender

                if (playerOnly && source.executor !is Player) {
                    sender.sendMessage("Only players can use this command")
                    return@executes Command.SINGLE_SUCCESS
                }

                cooldown
                    ?.takeIf { sender is Player }
                    ?.takeIf { it.isOnCooldown(sender as Player) }
                    ?.let {
                        it.sendMessage(sender as Player)
                        return@executes Command.SINGLE_SUCCESS
                    }

                val commandArgs =
                    args
                        .map { (name, clazz) ->
                            val result = ctx.getArgument(name, clazz)

                            CommandArgument(
                                when (result) {
                                    is SelectorArgumentResolver<*> -> result.resolve(ctx.source)
                                    else -> result
                                },
                            )
                        }

                block(sender, commandArgs)

                cooldown
                    ?.takeIf { sender is Player }
                    ?.startCooldown(sender as Player)

                Command.SINGLE_SUCCESS
            }
    }

    override fun requires(predicate: (CommandSourceStack) -> Boolean) {
        root = root.requires { predicate(it) }
    }

    override fun permission(permission: String?) {
        permission ?: return
        root = root.requires { it.sender.isOp || it.sender.hasPermission(permission) }
    }

    override fun playerOnly() {
        playerOnly = true
    }

    override fun staffOnly() {
        playerOnly()

        root =
            root.requires {
                it.sender.isOp ||
                    it.sender.hasPermission("galaxysky.staff.others")
            }
    }

    override fun adminOnly() {
        root = root.requires { it.sender.isOp }
    }

    override fun staffCanUseOnOthers() {
        optional<PlayerSelectorArgumentResolver>("player", ArgumentTypes.player()) { _, args ->
            @Suppress("UNCHECKED_CAST")
            block((args[0].value as List<Player>).first(), args.drop(0))
        }.configure { staffOnly() }.attach()
    }

    fun subcommand(
        name: String,
        function: (CommandSender, List<CommandArgument>) -> Unit,
    ) = internalSubcommand(name, function)

    inline fun <reified U : Any> argument(
        name: String,
        type: ArgumentType<U>,
    ) = internalArgument(name, type, U::class.java)

    inline fun <reified U : Any> optional(
        name: String,
        type: ArgumentType<U>,
        noinline function: (CommandSender, List<CommandArgument>) -> Unit,
    ) = internalOptional(name, type, U::class.java, function)

    override fun internalSubcommand(
        name: String,
        function: (CommandSender, List<CommandArgument>) -> Unit,
    ) = CommandBuilder(name, function, cooldown)

    override fun <U : Any> internalArgument(
        name: String,
        type: ArgumentType<U>,
        clazz: Class<U>,
    ) {
        root = root.then(Commands.argument(name, type))
        args.add(name to clazz)
    }

    override fun <U : Any> internalOptional(
        name: String,
        type: ArgumentType<U>,
        clazz: Class<U>,
        function: (CommandSender, List<CommandArgument>) -> Unit,
    ) = OptionalArgumentBuilder(name, type, function, cooldown).apply { args.add(name to clazz) }
}
