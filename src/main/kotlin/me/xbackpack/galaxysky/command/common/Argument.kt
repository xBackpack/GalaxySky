package me.xbackpack.galaxysky.command.common

import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.argument.resolvers.ArgumentResolver

@CommandDsl
data class Argument(
    val name: String,
) {
    inline fun <reified T> extract(ctx: CommandContext<CommandSourceStack>): T {
        val arg = ctx.getArgument(name, Any::class.java)

        return (arg as? ArgumentResolver<*>)
            ?.resolve(ctx.source) as T
            ?: arg as T
    }
}
