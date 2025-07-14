package me.xbackpack.galaxysky.command.api

import me.xbackpack.galaxysky.command.api.util.UserCooldown

@CommandDSL
data class Command(
    val builder: CommandBuilder,
    val description: String,
    val aliases: Collection<String> = emptyList(),
    val cooldown: UserCooldown?,
) {
    fun configure(block: CommandBuilder.() -> Unit) = this.also { builder.apply(block) }
}
