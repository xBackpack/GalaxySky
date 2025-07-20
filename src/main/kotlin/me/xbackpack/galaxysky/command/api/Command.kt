package me.xbackpack.galaxysky.command.api

import me.xbackpack.galaxysky.command.util.UserCooldown
import org.bukkit.command.CommandSender

@CommandDsl
data class Command(
    val root: CommandBuilder,
    val description: String,
    val aliases: Collection<String> = emptyList(),
) {
    fun configure(builder: CommandBuilder.() -> Unit) = this.also { root.apply(builder) }

    companion object {
        fun create(
            name: String,
            description: String,
            aliases: List<String>,
            permission: String?,
            cooldown: UserCooldown?,
            block: (CommandSender, List<CommandArgument>) -> Unit,
        ) = Command(
            CommandBuilder(name, permission, cooldown, block),
            description,
            aliases,
        )
    }
}
