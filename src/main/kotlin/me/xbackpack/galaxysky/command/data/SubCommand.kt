package me.xbackpack.galaxysky.command.data

import com.mojang.brigadier.builder.LiteralArgumentBuilder
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
data class SubCommand(
    override val name: String,
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

    fun build(): LiteralArgumentBuilder<CommandSourceStack> = internalBuild(Commands.literal(name))
}
