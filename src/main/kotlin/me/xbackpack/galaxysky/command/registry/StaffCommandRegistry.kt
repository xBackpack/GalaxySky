package me.xbackpack.galaxysky.command.registry

import me.xbackpack.galaxysky.command.data.Command
import me.xbackpack.galaxysky.enum.command.SenderRequirement
import me.xbackpack.galaxysky.item.registry.Pickaxes
import me.xbackpack.galaxysky.service.LocationService
import net.kyori.adventure.text.format.NamedTextColor

object StaffCommandRegistry {
    private val ADMIN_PICKAXE =
        Command.create {
            name = "adminpickaxe"
            description = "Gives the player the starter pickaxe"
            permission = "galaxysky.command.adminpickaxe"

            doForPlayer { _, _ ->
                giveItem(Pickaxes.ADMIN_PICKAXE)
            }
        }

    private val AREA =
        Command.create {
            name = "area"
            description = "Teleports the player to the specified area"
            requirement = SenderRequirement.STAFF

            doForPlayer { _, _ ->
                sendMessage {
                    text("Please specify a valid world") {
                        colour(NamedTextColor.RED)
                    }
                }
            }

            subcommand("staff") {
                permission = "galaxysky.area.staff"

                doForPlayer { _, _ ->
                    teleport(LocationService.STAFF_AREA)
                }
            }

            subcommand("builder") {
                permission = "galaxysky.area.builder"

                doForPlayer { _, _ ->
                    teleport(LocationService.BUILDER_AREA)
                }
            }
        }

    val commands =
        listOf(
            ADMIN_PICKAXE,
            AREA,
        )
}
