package me.xbackpack.galaxysky.command.impl

import me.xbackpack.galaxysky.command.api.Command
import me.xbackpack.galaxysky.command.api.CommandDsl
import me.xbackpack.galaxysky.command.util.UserCooldown

@CommandDsl
interface BaseCommand {
    var name: String
    var description: String
    var aliases: List<String>
    var permission: String?
    val cooldown: UserCooldown?

    fun create(): Command
}
