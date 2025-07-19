package me.xbackpack.galaxysky.command.impl

import me.xbackpack.galaxysky.command.api.Command
import me.xbackpack.galaxysky.command.api.CommandDsl
import me.xbackpack.galaxysky.command.util.UserCooldown
import me.xbackpack.galaxysky.message.Message
import org.bukkit.entity.Player

@CommandDsl
class MessageCommand : BaseCommand {
    override lateinit var name: String
    override lateinit var description: String
    override var aliases: List<String> = emptyList()
    override var permission: String? = null
    override var cooldown: UserCooldown? = null
    lateinit var message: Message

    override fun create() =
        Command
            .create(name, description, aliases, cooldown) { sender, _ ->
                val player = sender as Player

                player.sendMessage(message.root)
            }.configure {
                permission(this@MessageCommand.permission)
            }

    companion object {
        fun create(builder: MessageCommand.() -> Unit) = MessageCommand().apply(builder).create()
    }
}
