package me.xbackpack.galaxysky.command.registry.player

import me.xbackpack.galaxysky.command.api.Command
import me.xbackpack.galaxysky.command.impl.BaseCommand
import me.xbackpack.galaxysky.command.impl.ItemSupplyCommand
import me.xbackpack.galaxysky.command.util.UserCooldown
import me.xbackpack.galaxysky.common.RegistrySupplier
import me.xbackpack.galaxysky.item.registry.Pickaxes
import kotlin.time.Duration.Companion.seconds

object PlayerItemSupplyCommandRegistry : RegistrySupplier<Command> {
    override fun get(): List<Command> {
        val start =
            BaseCommand.create({ ItemSupplyCommand() }) {
                name = "start"
                description = "Gives the player the starter pickaxe"
                aliases = listOf("begin")
                cooldown = UserCooldown(60.seconds)
                item = Pickaxes.STONE_PICKAXE_1
            }

        return listOf(start)
    }
}
