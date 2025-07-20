package me.xbackpack.galaxysky.command.registry.staff

import me.xbackpack.galaxysky.command.api.Command
import me.xbackpack.galaxysky.command.impl.BaseCommand
import me.xbackpack.galaxysky.command.impl.ItemSupplyCommand
import me.xbackpack.galaxysky.common.RegistrySupplier
import me.xbackpack.galaxysky.item.registry.Pickaxes

object StaffItemSupplyCommandRegistry : RegistrySupplier<Command> {
    override fun get(): List<Command> {
        val adminPickaxe =
            BaseCommand.create({ ItemSupplyCommand() }) {
                name = "adminpickaxe"
                description = "Gives the player the admin pickaxe"
                permission = "galaxysky.command.adminpickaxe"
                item = Pickaxes.ADMIN_PICKAXE
            }

        return listOf(adminPickaxe)
    }
}
