package me.xbackpack.galaxysky.command.registry.staff

import me.xbackpack.galaxysky.command.api.Command
import me.xbackpack.galaxysky.command.impl.MiscPlayerCommand
import me.xbackpack.galaxysky.common.RegistrySupplier
import me.xbackpack.galaxysky.item.registry.Pickaxes

object StaffMiscCommandRegistry : RegistrySupplier<Command> {
    override fun get(): List<Command> {
        val adminPickaxe =
            MiscPlayerCommand.create {
                name = "adminpickaxe"
                description = "Gives the player the admin pickaxe"
                permission = "galaxysky.command.adminpickaxe"
                function = { player, _ ->
                    val item = Pickaxes.ADMIN_PICKAXE

                    player.inventory.addItem(item.build())
                }
            }

        return listOf(adminPickaxe)
    }
}
