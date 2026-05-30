package me.xbackpack.galaxysky.api.command.branches

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.tree.ArgumentCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import me.xbackpack.galaxysky.api.command.Argument
import me.xbackpack.galaxysky.api.command.Permission
import me.xbackpack.galaxysky.api.command.Requirement
import me.xbackpack.galaxysky.api.command.types.Executable
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

class OptionalArgument(
    override val name: String,
    val type: ArgumentType<*>,
    override val requirement: Requirement,
    override val permission: Permission?,
    val factory: OptionalArgument.() -> Unit,
) : Executable<ArgumentCommandNode<CommandSourceStack, *>> {
    override fun doForPlayer(block: (Player, List<Argument>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun doForConsole(block: (ConsoleCommandSender, List<Argument>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun build(): ArgumentCommandNode<CommandSourceStack, *> = Commands.argument(name, type).build()
}
