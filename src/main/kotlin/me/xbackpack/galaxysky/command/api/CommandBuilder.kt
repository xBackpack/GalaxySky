package me.xbackpack.galaxysky.command.api

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import me.xbackpack.galaxysky.command.api.util.UserCooldown
import me.xbackpack.galaxysky.util.Builder

@CommandDsl
class CommandBuilder(
    name: String,
    block: CommandFunction,
    cooldown: UserCooldown?,
) : AbstractCommandNode<LiteralArgumentBuilder<CommandSourceStack>>(Commands.literal(name), block, cooldown),
    Builder<LiteralCommandNode<CommandSourceStack>> {
    override fun build(): LiteralCommandNode<CommandSourceStack> = root.build()

    fun configure(block: CommandBuilder.() -> Unit) = this.apply(block)

    fun attach() {
        root.then(this.build())
    }
}
