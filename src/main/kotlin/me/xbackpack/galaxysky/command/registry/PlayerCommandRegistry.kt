package me.xbackpack.galaxysky.command.registry

import me.xbackpack.galaxysky.command.common.Cooldown
import me.xbackpack.galaxysky.command.data.Command
import me.xbackpack.galaxysky.enum.command.SenderRequirement
import me.xbackpack.galaxysky.hook.PlaceholderHook
import me.xbackpack.galaxysky.hook.PlaceholderHook.SHOP_LINK
import me.xbackpack.galaxysky.item.registry.Pickaxes
import me.xbackpack.galaxysky.service.LocationService
import net.kyori.adventure.text.format.NamedTextColor
import kotlin.time.Duration.Companion.seconds

object PlayerCommandRegistry {
    private val WORLD =
        Command.create {
            name = "world"
            description = "Teleports the player to the specified world"
            requirement = SenderRequirement.PLAYER

            doForPlayer { _, _ ->
                sendMessage {
                    text("Please specify a valid world") {
                        colour(NamedTextColor.RED)
                    }
                }
            }

            subcommand("spawn") {
                doForPlayer { _, _ ->
                    teleport(LocationService.WORLD_SPAWN)
                }
            }

            subcommand("nether") {
                permission = "galaxysky.world.nether"

                doForPlayer { _, _ ->
                    teleport(LocationService.NETHER_SPAWN)
                }
            }

            subcommand("end") {
                permission = "galaxysky.world.end"

                doForPlayer { _, _ ->
                    teleport(LocationService.END_SPAWN)
                }
            }

            subcommand("aether") {
                permission = "galaxysky.world.aether"

                doForPlayer { _, _ ->
                    teleport(LocationService.AETHER_SPAWN)
                }
            }

            subcommand("staff") {
                permission = "galaxysky.world.staff"

                doForPlayer { _, _ ->
                    teleport(LocationService.STAFF_WORLD_SPAWN)
                }
            }
        }

    private val AFK =
        Command.create {
            name = "afk"
            description = "Teleports the player to the AFK area"
            requirement = SenderRequirement.PLAYER

            doForPlayer { _, _ ->
                teleport(LocationService.AFK)
            }
        }

    private val APPLY =
        Command.create {
            name = "apply"
            description = "Provides a link to the staff application"
            requirement = SenderRequirement.PLAYER

            doForPlayer { _, _ ->
                sendMessage {
                    section {
                        text("Apply on our discord!")

                        newline()

                        text("Link:")

                        space()

                        link(PlaceholderHook.DISCORD)

                        colour(NamedTextColor.LIGHT_PURPLE)
                    }
                }
            }
        }

    private val IP =
        Command.create {
            name = "ip"
            description = "Provides the IP of the server"
            aliases = listOf("serverip")
            requirement = SenderRequirement.PLAYER

            doForPlayer { _, _ ->
                sendMessage {
                    section {
                        text("The GalaxySky IP is:")

                        space()

                        snippet(PlaceholderHook.SERVER_IP)

                        colour(NamedTextColor.LIGHT_PURPLE)
                    }
                }
            }
        }

    private val SHOP =
        Command.create {
            name = "shop"
            description = "Provides a link to the GalaxySky webstore"
            aliases = listOf("store", "webstore")
            requirement = SenderRequirement.PLAYER

            doForPlayer { _, _ ->
                sendMessage {
                    section {
                        text("The GalaxySky shop link is:")
                        space()

                        link(SHOP_LINK)

                        colour(NamedTextColor.LIGHT_PURPLE)
                    }
                }
            }
        }

    private val START =
        Command.create {
            name = "start"
            description = "Gives the player the starter pickaxe"
            aliases = listOf("begin")
            requirement = SenderRequirement.PLAYER
            cooldown = Cooldown(60.seconds)

            doForPlayer { _, _ ->
                giveItem(Pickaxes.STONE_PICKAXE_1)
            }
        }

    val commands =
        listOf(
            WORLD,
            AFK,
            APPLY,
            IP,
            SHOP,
            START,
        )
}
