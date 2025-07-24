package me.xbackpack.galaxysky.command.registry.staff

import me.xbackpack.galaxysky.command.impl.BaseCommand
import me.xbackpack.galaxysky.command.impl.ItemSupplyCommand
import me.xbackpack.galaxysky.item.registry.Pickaxes

object StaffItemSupplyCommandRegistry {
    val commands =
        listOf(
            BaseCommand.create({ ItemSupplyCommand() }) {
                name = "adminpickaxe"
                description = "Gives the player the admin pickaxe"
                permission = "galaxysky.command.adminpickaxe"
                item = Pickaxes.ADMIN_PICKAXE
            },
        )
}
