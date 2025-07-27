package me.xbackpack.galaxysky.command.registry

import me.xbackpack.galaxysky.GalaxySky
import me.xbackpack.galaxysky.command.common.Cooldown
import me.xbackpack.galaxysky.command.data.Command
import me.xbackpack.galaxysky.enum.command.SenderRequirement
import me.xbackpack.galaxysky.enum.player.PlayerStatType
import me.xbackpack.galaxysky.hook.PlaceholderHook
import me.xbackpack.galaxysky.hook.PlaceholderHook.SHOP_LINK
import me.xbackpack.galaxysky.item.registry.Pickaxes
import me.xbackpack.galaxysky.message.Message
import me.xbackpack.galaxysky.message.sendMessage
import me.xbackpack.galaxysky.service.FormattingService
import me.xbackpack.galaxysky.service.LocationService
import me.xbackpack.galaxysky.service.PDCService
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.event.player.PlayerDropItemEvent
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
                requirement = SenderRequirement.STAFF

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

    private val PLAYTIME =
        Command.create {
            name = "playtime"
            description = "Sends the player their full playtime"
            requirement = SenderRequirement.PLAYER

            doForPlayer { player, _ ->
                val playtime = PDCService.Player.Stats[player, PlayerStatType.PLAYTIME]

                sendMessage {
                    section {
                        text("You have")

                        space()

                        text(FormattingService.shortenTimeFull(playtime))

                        space()

                        text("of playtime!")

                        colour(NamedTextColor.GREEN)
                    }
                }
            }
        }

    private val DROPS =
        Command.create {
            val cmdCooldown =
                Cooldown(10.seconds) { _ ->
                    Message.create {
                        text("You can already drop items!") {
                            colour(NamedTextColor.RED)
                        }
                    }
                }

            name = "drops"
            description = "Allows the player to drop items for 10 seconds"
            cooldown = cmdCooldown
            requirement = SenderRequirement.PLAYER

            doForPlayer { player, _ ->
                sendMessage {
                    text("You can now drop items for the next 10 seconds.") {
                        colour(NamedTextColor.GREEN)
                    }
                }

                GalaxySky.runTaskLater(10.seconds) {
                    player.sendMessage(
                        Message.create {
                            text("Use [/drop] to drop items!") {
                                colour(NamedTextColor.RED)
                            }
                        },
                    )
                }
            }

            listener<PlayerDropItemEvent> { event ->
                val player = event.player

                if (!cmdCooldown.isOnCooldown(player)) {
                    player.sendMessage(
                        Message.create {
                            text("Use [/drop] to drop items!") {
                                colour(NamedTextColor.RED)
                            }
                        },
                    )
                    event.isCancelled = true
                }
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
            PLAYTIME,
            DROPS,
        )
}
