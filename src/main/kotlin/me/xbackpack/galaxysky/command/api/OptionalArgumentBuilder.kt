package me.xbackpack.galaxysky.command.api

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.tree.ArgumentCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import me.xbackpack.galaxysky.command.api.util.UserCooldown
import me.xbackpack.galaxysky.common.Builder

@CommandDsl
class OptionalArgumentBuilder<T : Any>(
    name: String,
    type: ArgumentType<T>,
    block: CommandFunction,
    cooldown: UserCooldown?,
) : AbstractCommandNode<RequiredArgumentBuilder<CommandSourceStack, T>>(Commands.argument(name, type), block, cooldown),
    Builder<ArgumentCommandNode<CommandSourceStack, T>> {
    override fun build(): ArgumentCommandNode<CommandSourceStack, T> = root.build()

    fun configure(block: OptionalArgumentBuilder<T>.() -> Unit) = this.apply(block)

    fun attach() {
        root.then(this.build())
    }
}
