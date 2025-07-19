package me.xbackpack.galaxysky.command.registry.player

import me.xbackpack.galaxysky.command.api.Command
import me.xbackpack.galaxysky.command.impl.MiscPlayerCommand
import me.xbackpack.galaxysky.command.util.UserCooldown
import me.xbackpack.galaxysky.common.RegistrySupplier
import me.xbackpack.galaxysky.item.registry.Pickaxes
import kotlin.time.Duration.Companion.seconds

object PlayerMiscCommandRegistry : RegistrySupplier<Command> {
    override fun get(): List<Command> {
        val start =
            MiscPlayerCommand.create {
                name = "start"
                description = "Gives the player the starter pickaxe"
                aliases = listOf("begin")
                cooldown = UserCooldown(60.seconds)
                function = { player, _ ->
                    val item = Pickaxes.STONE_PICKAXE_1

                    player.inventory.addItem(item.build())
                }
            }

        return listOf(start)
    }
}
