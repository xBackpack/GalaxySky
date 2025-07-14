package me.xbackpack.galaxysky.command.api

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver
import io.papermc.paper.command.brigadier.argument.resolvers.selector.SelectorArgumentResolver
import me.xbackpack.galaxysky.command.api.util.UserCooldown
import org.bukkit.entity.Player

@CommandDSL
abstract class AbstractCommandNode<T : ArgumentBuilder<CommandSourceStack, T>>(
    final override var root: T,
    override val block: CommandFunction,
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

                block.perform(sender, commandArgs)

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
        optional("player", ArgumentTypes.player(), PlayerSelectorArgumentResolver::class.java, { _, args ->
            @Suppress("UNCHECKED_CAST")
            block.perform((args[0].value as List<Player>).first(), args.drop(0))
        }) { staffOnly() }
    }

    override fun subcommand(
        name: String,
        function: CommandFunction,
        builder: CommandBuilder.() -> Unit,
    ) {
        val sub = CommandBuilder(name, function, cooldown).apply(builder).build()
        root = root.then(sub)
    }

    override fun <U : Any> argument(
        name: String,
        type: ArgumentType<U>,
        clazz: Class<U>,
    ) {
        root = root.then(Commands.argument(name, type))
        args.add(name to clazz)
    }

    override fun <U : Any> optional(
        name: String,
        type: ArgumentType<U>,
        clazz: Class<U>,
        function: CommandFunction,
        builder: OptionalArgumentBuilder<U>.() -> Unit,
    ) {
        val child =
            OptionalArgumentBuilder(name, type, function, cooldown).apply {
                args.add(name to clazz)

                builder()
            }
        root = root.then(child.build())
    }
}
