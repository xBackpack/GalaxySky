package me.xbackpack.galaxysky.command.registry.player

import me.xbackpack.galaxysky.command.api.Command
import me.xbackpack.galaxysky.command.api.util.UserCooldown
import me.xbackpack.galaxysky.command.impl.MiscPlayerCommand
import me.xbackpack.galaxysky.command.impl.util.PlayerCommandFunction
import me.xbackpack.galaxysky.common.Registry
import kotlin.time.Duration.Companion.seconds

object PlayerMiscCommandRegistry : Registry<Command> {
    override fun init(): List<Command> {
        val start =
            MiscPlayerCommand.create {
                name = "start"
                description = "Gives the player the starter pickaxe"
                aliases = listOf("begin")
                cooldown = UserCooldown(60.seconds)
                function =
                    PlayerCommandFunction { player, _ ->
                        TODO("BuildItem")
                    }
            }

        return listOf(start)
    }
}
