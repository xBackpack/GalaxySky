package me.xbackpack.galaxysky.command.api

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.command.CommandSender

@CommandDsl
interface CommandNode<T : ArgumentBuilder<CommandSourceStack, T>> {
    var root: T
    val permission: String?
    val cooldown: UserCooldown?
    val block: (CommandSender, List<CommandArgument>) -> Unit
    var playerOnly: Boolean

    fun requires(predicate: (CommandSourceStack) -> Boolean)

    fun playerOnly()

    fun staffOnly()

    fun adminOnly()

    fun staffCanUseOnOthers()

    fun internalSubcommand(
        name: String,
        function: (CommandSender, List<CommandArgument>) -> Unit,
    ): CommandBuilder

    fun <U : Any> internalArgument(
        name: String,
        type: ArgumentType<U>,
        clazz: Class<U>,
    )

    fun <U : Any> internalOptional(
        name: String,
        type: ArgumentType<U>,
        clazz: Class<U>,
        function: (CommandSender, List<CommandArgument>) -> Unit,
    ): OptionalArgumentBuilder<U>
}
