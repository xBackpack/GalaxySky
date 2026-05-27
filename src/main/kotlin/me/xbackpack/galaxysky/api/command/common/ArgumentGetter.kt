package me.xbackpack.galaxysky.api.command.common

import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.argument.resolvers.selector.SelectorArgumentResolver

@CommandDsl
data class ArgumentGetter(
    val context: CommandContext<CommandSourceStack>,
) {
    inline fun <reified T : Any> extract(name: String): T = context.getArgument(name, T::class.java)

    inline fun <reified T : SelectorArgumentResolver<List<U>>, reified U : Any> extractAndResolveFirst(name: String): U {
        val arg = context.getArgument(name, T::class.java)

        return arg.resolve(context.source).first()
    }

    inline fun <reified T : SelectorArgumentResolver<List<U>>, reified U : Any> extractAndResolve(name: String): List<U> {
        val arg = context.getArgument(name, T::class.java)

        return arg.resolve(context.source)
    }
}
