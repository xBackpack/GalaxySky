package me.xbackpack.galaxysky.registry.command

import me.xbackpack.galaxysky.api.old.data.Command
import me.xbackpack.galaxysky.enum.command.SenderRequirement

object TestCommandRegistry {
    val CALCULATE_MINING_SPEED =
        Command.create("miningspeed", "Sends the player the mining speed of their tool", SenderRequirement.STAFF()) {
        }

    val commands = listOf(CALCULATE_MINING_SPEED)
}
