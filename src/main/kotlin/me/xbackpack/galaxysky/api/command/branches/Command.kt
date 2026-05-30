package me.xbackpack.galaxysky.api.command.branches

import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import me.xbackpack.galaxysky.api.command.Argument
import me.xbackpack.galaxysky.api.command.Cooldown
import me.xbackpack.galaxysky.api.command.Permission
import me.xbackpack.galaxysky.api.command.Requirement
import me.xbackpack.galaxysky.api.command.types.Branch
import me.xbackpack.galaxysky.api.command.types.Executable
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

class Command(
    override val name: String,
    val description: String,
    override val requirement: Requirement,
    override val permission: Permission? = null,
    val aliases: List<String> = emptyList(),
    override val cooldown: Cooldown? = null,
) : Branch,
    Executable<LiteralCommandNode<CommandSourceStack>> {
    override val arguments = mutableListOf<Argument>()
    override val optionals = mutableListOf<OptionalArgument>()
    override val subcommands = mutableListOf<Subcommand>()

    override fun doForPlayer(block: (Player, List<Argument>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun doForConsole(block: (ConsoleCommandSender, List<Argument>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun build(): LiteralCommandNode<CommandSourceStack> = Commands.literal("name").build()
}
