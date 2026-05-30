package me.xbackpack.galaxysky.api.old.data

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import me.xbackpack.galaxysky.api.old.common.CommandDsl

@CommandDsl
data class RequiredArgument(
    val name: String,
    val type: ArgumentType<*>,
) {
    override fun equals(other: Any?): Boolean = other is RequiredArgument && other.name == name

    override fun hashCode() = name.hashCode()

    fun build(): RequiredArgumentBuilder<CommandSourceStack, *> = Commands.argument(name, type)
}
