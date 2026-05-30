package me.xbackpack.galaxysky.api.command

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.command.brigadier.argument.resolvers.selector.SelectorArgumentResolver

data class Argument(
    val name: String,
    val type: ArgumentType<*>,
) {
    fun build() = Commands.argument(name, type)

    inline fun <reified T : Any> extract(ctx: CommandContext<CommandSourceStack>) = ctx.getArgument(name, T::class.java)!!

    inline fun <reified T : SelectorArgumentResolver<List<U>>, reified U : Any> extractAndResolve(ctx: CommandContext<CommandSourceStack>) =
        ctx.getArgument(name, T::class.java).resolve(ctx.source)

    inline fun <reified T : SelectorArgumentResolver<List<U>>, reified U : Any> extractAndResolveFirst(
        ctx: CommandContext<CommandSourceStack>,
    ) = extractAndResolve<T, U>(ctx).first()
}
