package me.xbackpack.galaxysky.command.data

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import me.xbackpack.galaxysky.command.common.CommandDsl
import me.xbackpack.galaxysky.command.common.Cooldown
import me.xbackpack.galaxysky.command.function.ConsoleCommandFunction
import me.xbackpack.galaxysky.command.function.PlayerCommandFunction
import me.xbackpack.galaxysky.command.function.StaffCommandFunction
import me.xbackpack.galaxysky.command.node.Node
import me.xbackpack.galaxysky.enum.command.SenderRequirement

@CommandDsl
data class OptionalArgument(
    override val name: String,
    val type: ArgumentType<*>,
) : Node {
    override lateinit var requirement: SenderRequirement
    override var permission: String? = null
    override var cooldown: Cooldown? = null

    override var playerFunction: PlayerCommandFunction? = null
    override var staffFunction: StaffCommandFunction? = null
    override var consoleFunction: ConsoleCommandFunction? = null

    override val subcommands: MutableSet<SubCommand> = mutableSetOf()
    override val optionals: MutableSet<OptionalArgument> = mutableSetOf()
    override val arguments: MutableSet<RequiredArgument> = mutableSetOf()

    override fun equals(other: Any?): Boolean = other is OptionalArgument && other.name == name

    override fun hashCode() = name.hashCode()

    fun build(): RequiredArgumentBuilder<CommandSourceStack, *> = internalBuild(Commands.argument(name, type))
}
