package me.xbackpack.galaxysky.api.command.types

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack

interface Argument {
    val name: String
    val type: ArgumentType<*>

    var ctx: CommandContext<CommandSourceStack>
}
