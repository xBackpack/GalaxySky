package me.xbackpack.galaxysky.api.util

import io.papermc.paper.command.brigadier.argument.resolvers.selector.SelectorArgumentResolver
import me.xbackpack.galaxysky.api.command.types.Argument
import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.enum.Colour
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

// Command DSL Helpers
operator fun List<Argument>.get(name: String) = first { it.name == name }

typealias PlayerFunction = (Player, List<Argument>) -> Unit

typealias ConsoleFunction = (ConsoleCommandSender, List<Argument>) -> Unit

inline fun <reified T : Any> Argument.extract() = ctx.getArgument(name, T::class.java)!!

inline fun <reified T : SelectorArgumentResolver<List<U>>, reified U : Any> Argument.extractAndResolve() =
    ctx.getArgument(name, T::class.java).resolve(ctx.source)

inline fun <reified T : SelectorArgumentResolver<List<U>>, reified U : Any> Argument.extractAndResolveFirst() =
    extractAndResolve<T, U>().first()

// Util functions
inline fun <reified T : Entity> Location.spawnEntity(settings: T.() -> Unit) {
    world
        .spawn(this, T::class.java)
        .apply(settings)
}

fun Player.updateGameMode(
    mode: GameMode,
    executor: CommandSender? = null,
) {
    executor?.let { executor ->
        executor.msg {
            text("You updated $name's game to ${mode.name.lowercase()}!") {
                colour(Colour.GREEN)
            }
        }
    }

    msg {
        text("Your gamemode has been updated to ${mode.name.lowercase()}!") {
            colour(Colour.GREEN)
        }
    }

    gameMode = mode
}

fun CommandSender.msg(builder: Message.() -> Unit) {
    msg(Message().apply(builder))
}

fun CommandSender.msg(message: Message) {
    sendMessage(message.build())
}
