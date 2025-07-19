package me.xbackpack.galaxysky.command.registry.player

import me.xbackpack.galaxysky.command.api.Command
import me.xbackpack.galaxysky.command.impl.MiscPlayerCommand
import me.xbackpack.galaxysky.command.impl.util.PlayerCommandFunction
import me.xbackpack.galaxysky.command.util.UserCooldown
import me.xbackpack.galaxysky.common.RegistrySupplier
import me.xbackpack.galaxysky.item.registry.PickaxeRegistry
import kotlin.time.Duration.Companion.seconds

object PlayerMiscCommandRegistry : RegistrySupplier<Command> {
    override fun get(): List<Command> {
        val start =
            MiscPlayerCommand.create {
                name = "start"
                description = "Gives the player the starter pickaxe"
                aliases = listOf("begin")
                cooldown = UserCooldown(60.seconds)
                function =
                    PlayerCommandFunction { player, _ ->
                        val item = PickaxeRegistry.getPickaxe("stone_pickaxe_1")

                        player.inventory.addItem(item.build())
                    }
            }

        return listOf(start)
    }
}
