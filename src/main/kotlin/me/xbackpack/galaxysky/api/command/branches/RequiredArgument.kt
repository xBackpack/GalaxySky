package me.xbackpack.galaxysky.api.command.branches

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import me.xbackpack.galaxysky.api.command.types.Argument

data class RequiredArgument(
    override val name: String,
    override val type: ArgumentType<*>,
) : Argument {
    override lateinit var ctx: CommandContext<CommandSourceStack>

    fun build() = Commands.argument(name, type)
}
