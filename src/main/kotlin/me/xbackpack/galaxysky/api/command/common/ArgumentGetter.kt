package me.xbackpack.galaxysky.api.command.common

import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.argument.resolvers.ArgumentResolver

@CommandDsl
data class ArgumentGetter(
    val context: CommandContext<CommandSourceStack>,
) {
    inline fun <reified T> extract(name: String): T {
        val arg = context.getArgument(name, Any::class.java)

        return (arg as? ArgumentResolver<*>)
            ?.resolve(context.source) as T
            ?: arg as T
    }
}
