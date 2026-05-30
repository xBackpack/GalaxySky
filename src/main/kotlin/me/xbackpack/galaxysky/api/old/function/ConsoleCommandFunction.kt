package me.xbackpack.galaxysky.api.old.function

import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.api.message.MessageBuilder
import me.xbackpack.galaxysky.api.old.common.ArgumentGetter
import me.xbackpack.galaxysky.api.old.common.CommandDsl
import me.xbackpack.galaxysky.sendMessage
import org.bukkit.command.ConsoleCommandSender

@CommandDsl
class ConsoleCommandFunction(
    private val console: ConsoleCommandSender,
    private val getter: ArgumentGetter,
) : CommandFunction {
    override val functions = mutableListOf<() -> Unit>()

    fun sendMessage(message: MessageBuilder.() -> Unit) {
        functions.add {
            console.sendMessage(Message.create(message))
        }
    }
}
