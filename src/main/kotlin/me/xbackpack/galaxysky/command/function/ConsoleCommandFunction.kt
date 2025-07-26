package me.xbackpack.galaxysky.command.function

import me.xbackpack.galaxysky.command.common.Argument
import me.xbackpack.galaxysky.command.common.CommandDsl
import me.xbackpack.galaxysky.command.common.ConsoleFunction
import me.xbackpack.galaxysky.message.Message
import me.xbackpack.galaxysky.message.sendMessage
import org.bukkit.command.ConsoleCommandSender

@CommandDsl
class ConsoleCommandFunction {
    fun runAll(
        console: ConsoleCommandSender,
        args: List<Argument>,
    ) {
        functions.forEach { it.execute(console, args) }
    }

    private val functions = mutableListOf<ConsoleFunction<Unit>>()

    fun sendMessage(messageFactory: ConsoleFunction<Message>) {
        functions.add { console, args ->
            console.sendMessage(messageFactory.execute(console, args))
        }
    }
}
