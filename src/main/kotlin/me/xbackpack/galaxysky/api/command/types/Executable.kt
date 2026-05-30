package me.xbackpack.galaxysky.api.command.types

import com.mojang.brigadier.tree.CommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import me.xbackpack.galaxysky.api.command.Argument
import me.xbackpack.galaxysky.api.command.Permission
import me.xbackpack.galaxysky.api.command.Requirement
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

interface Executable<T : CommandNode<CommandSourceStack>> {
    val name: String
    val requirement: Requirement
    val permission: Permission?

    fun doForPlayer(block: (Player, List<Argument>) -> Unit)

    fun doForConsole(block: (ConsoleCommandSender, List<Argument>) -> Unit)

    fun build(): T
}
