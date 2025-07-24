package me.xbackpack.galaxysky.command.registry.player

import me.xbackpack.galaxysky.command.api.UserCooldown
import me.xbackpack.galaxysky.command.impl.BaseCommand
import me.xbackpack.galaxysky.command.impl.ItemSupplyCommand
import me.xbackpack.galaxysky.item.registry.Pickaxes
import kotlin.time.Duration.Companion.seconds

object PlayerItemSupplyCommandRegistry {
    val commands =
        listOf(
            BaseCommand.create({ ItemSupplyCommand() }) {
                name = "start"
                description = "Gives the player the starter pickaxe"
                aliases = listOf("begin")
                cooldown = UserCooldown(60.seconds)
                item = Pickaxes.STONE_PICKAXE_1
            },
        )
}
