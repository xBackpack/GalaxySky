package me.xbackpack.galaxysky.api.command.branches

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.ArgumentCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import me.xbackpack.galaxysky.api.command.CommandDsl
import me.xbackpack.galaxysky.api.command.Permission
import me.xbackpack.galaxysky.api.command.Requirement
import me.xbackpack.galaxysky.api.command.types.Argument
import me.xbackpack.galaxysky.api.command.types.Branch
import me.xbackpack.galaxysky.api.command.types.Executable
import me.xbackpack.galaxysky.api.util.ConsoleFunction
import me.xbackpack.galaxysky.api.util.PlayerFunction

@CommandDsl
class OptionalArgument(
    override val name: String,
    override val type: ArgumentType<*>,
    override val requirement: Requirement,
    override val permission: Permission?,
    override val parent: Branch,
) : Argument,
    Executable {
    override val playerFunctions = mutableListOf<PlayerFunction>()
    override val consoleFunctions = mutableListOf<ConsoleFunction>()

    override lateinit var ctx: CommandContext<CommandSourceStack>

    fun build(): ArgumentCommandNode<CommandSourceStack, *> = Commands.argument(name, type).buildTo(null, parent.arguments + this).build()
}
