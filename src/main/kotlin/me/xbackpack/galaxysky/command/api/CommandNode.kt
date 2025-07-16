package me.xbackpack.galaxysky.command.api

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import io.papermc.paper.command.brigadier.CommandSourceStack
import me.xbackpack.galaxysky.command.api.util.UserCooldown

@CommandDsl
interface CommandNode<T : ArgumentBuilder<CommandSourceStack, T>> {
    var root: T
    val block: CommandFunction
    val cooldown: UserCooldown?
    var playerOnly: Boolean

    fun requires(predicate: (CommandSourceStack) -> Boolean)

    fun permission(permission: String?)

    fun playerOnly()

    fun staffOnly()

    fun adminOnly()

    fun staffCanUseOnOthers()

    fun subcommand(
        name: String,
        function: CommandFunction,
    ): CommandBuilder

    fun <U : Any> argument(
        name: String,
        type: ArgumentType<U>,
        clazz: Class<U>,
    )

    fun <U : Any> optional(
        name: String,
        type: ArgumentType<U>,
        clazz: Class<U>,
        function: CommandFunction,
    ): OptionalArgumentBuilder<U>
}
