package me.xbackpack.galaxysky.command.impl

import me.xbackpack.galaxysky.command.api.util.UserCooldown
import me.xbackpack.galaxysky.command.impl.util.PlayerCommandFunction
import me.xbackpack.galaxysky.util.Registry.Companion.buildCommand
import org.bukkit.entity.Player

class MiscPlayerCommand : BaseCommand {
    override lateinit var name: String
    override lateinit var description: String
    override var aliases: List<String> = emptyList()
    override var permission: String? = null
    override var cooldown: UserCooldown? = null
    lateinit var function: PlayerCommandFunction

    override fun create() =
        buildCommand(name, description, aliases, cooldown) { sender, args ->
            val player = sender as Player

            function.perform(player, args)
        }.configure {
            playerOnly()

            permission(this@MiscPlayerCommand.permission)
        }

    companion object {
        fun create(builder: MiscPlayerCommand.() -> Unit) = MiscPlayerCommand().apply(builder).create()
    }
}
