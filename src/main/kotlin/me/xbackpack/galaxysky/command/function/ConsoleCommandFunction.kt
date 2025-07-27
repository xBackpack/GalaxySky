package me.xbackpack.galaxysky.command.function

import me.xbackpack.galaxysky.command.common.ArgumentGetter
import me.xbackpack.galaxysky.command.common.CommandDsl
import me.xbackpack.galaxysky.message.Message
import me.xbackpack.galaxysky.message.MessageBuilder
import me.xbackpack.galaxysky.message.sendMessage
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
