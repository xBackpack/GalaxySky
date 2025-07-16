package me.xbackpack.galaxysky.command.api

import me.xbackpack.galaxysky.command.api.util.UserCooldown

@CommandDsl
data class Command(
    val root: CommandBuilder,
    val description: String,
    val aliases: Collection<String> = emptyList(),
    val cooldown: UserCooldown?,
) {
    fun configure(builder: CommandBuilder.() -> Unit) = this.also { root.apply(builder) }

    companion object {
        fun create(
            name: String,
            description: String,
            aliases: List<String>,
            cooldown: UserCooldown?,
            block: CommandFunction,
        ) = Command(
            CommandBuilder(name, block, cooldown),
            description,
            aliases,
            cooldown,
        )
    }
}
