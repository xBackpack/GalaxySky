package me.xbackpack.galaxysky.command.data

import com.mojang.brigadier.arguments.ArgumentType
import io.papermc.paper.command.brigadier.Commands
import me.xbackpack.galaxysky.command.common.CommandDsl

@CommandDsl
data class RequiredArgument(
    val name: String,
    val type: ArgumentType<*>,
) {
    override fun equals(other: Any?): Boolean = other is RequiredArgument && other.name == name

    override fun hashCode() = name.hashCode()

    fun build() = Commands.argument(name, type)
}
