package me.xbackpack.galaxysky.command.api

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import me.xbackpack.galaxysky.common.Builder
import org.bukkit.command.CommandSender

@CommandDsl
class CommandBuilder(
    name: String,
    permission: String?,
    cooldown: UserCooldown?,
    block: (CommandSender, List<CommandArgument>) -> Unit,
) : AbstractCommandNode<LiteralArgumentBuilder<CommandSourceStack>>(Commands.literal(name), permission, cooldown, block),
    Builder<LiteralCommandNode<CommandSourceStack>> {
    override fun build(): LiteralCommandNode<CommandSourceStack> = root.build()

    fun configure(block: CommandBuilder.() -> Unit) = this.apply(block)

    fun attach() {
        root.then(this.build())
    }
}
