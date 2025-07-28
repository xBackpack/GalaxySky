package me.xbackpack.galaxysky.command.registry

import me.xbackpack.galaxysky.command.data.Command
import me.xbackpack.galaxysky.enum.command.SenderRequirement
import me.xbackpack.galaxysky.item.registry.Pickaxes
import me.xbackpack.galaxysky.service.LocationService
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.GameMode

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

    private val GMC =
        Command.create {
            name = "gmc"
            description = "Updates the player's game mode to creative"
            aliases = listOf("creative")
            permission = "galaxysky.gamemode.creative"

            doForPlayer { player, _ ->
                sendMessage {
                    text("Your gamemode has been updated to creative!") {
                        colour(NamedTextColor.GREEN)
                    }
                }

                updateGameMode(GameMode.CREATIVE)
            }
        }

    private val GMS =
        Command.create {
            name = "gms"
            description = "Updates the player's game mode to survival"
            aliases = listOf("survival")
            permission = "galaxysky.gamemode.survival"

            doForPlayer { player, _ ->
                sendMessage {
                    text("Your gamemode has been updated to survival!") {
                        colour(NamedTextColor.GREEN)
                    }
                }

                updateGameMode(GameMode.SURVIVAL)
            }
        }

    private val GMSP =
        Command.create {
            name = "gmsp"
            description = "Updates the player's game mode to spectator"
            aliases = listOf("spectator")
            permission = "galaxysky.gamemode.spectator"

            doForPlayer { player, _ ->
                sendMessage {
                    text("Your gamemode has been updated to spectator!") {
                        colour(NamedTextColor.GREEN)
                    }
                }

                updateGameMode(GameMode.SPECTATOR)
            }
        }

    private val GMA =
        Command.create {
            name = "gma"
            description = "Updates the player's game mode to adventure"
            aliases = listOf("adventure")
            permission = "galaxysky.gamemode.adventure"

            doForPlayer { player, _ ->
                sendMessage {
                    text("Your gamemode has been updated to adventure!") {
                        colour(NamedTextColor.GREEN)
                    }
                }

                updateGameMode(GameMode.ADVENTURE)
            }
        }

    val commands =
        listOf(
            ADMIN_PICKAXE,
            AREA,
            GMC,
            GMS,
            GMSP,
            GMA,
        )
}
