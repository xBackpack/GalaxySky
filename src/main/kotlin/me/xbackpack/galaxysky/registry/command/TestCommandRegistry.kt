package me.xbackpack.galaxysky.registry.command

import me.xbackpack.galaxysky.api.command.Requirement
import me.xbackpack.galaxysky.api.item.CustomItem
import me.xbackpack.galaxysky.api.util.command
import me.xbackpack.galaxysky.api.util.msg
import me.xbackpack.galaxysky.enum.Colour
import org.bukkit.attribute.Attribute

object TestCommandRegistry {
    private val CALCULATE_MINING_SPEED =
        command(
            name = "miningspeed",
            description = "Sends the player the mining speed of their tool",
            requirement = Requirement.STAFF,
        ) {
            doForPlayer { player, _ ->
                val tool = player.inventory.itemInMainHand

                val toolSpeed = CustomItem.getNaturalStrengthOfTool(tool.type)

                val miningEfficiency = player.getAttribute(Attribute.MINING_EFFICIENCY)?.value ?: 0.0
                val blockBreakSpeed = player.getAttribute(Attribute.BLOCK_BREAK_SPEED)?.value ?: 1.0

                val miningSpeed = (toolSpeed + miningEfficiency) * blockBreakSpeed * 100

                player.msg {
                    text(miningSpeed.toString()) {
                        colour(Colour.GREEN)
                    }
                }
            }
        }

    val commands = listOf(CALCULATE_MINING_SPEED)
}
