package me.xbackpack.galaxysky.registry.command

import me.xbackpack.galaxysky.GalaxySky
import me.xbackpack.galaxysky.api.command.common.Cooldown
import me.xbackpack.galaxysky.api.command.data.Command
import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.enum.command.SenderRequirement
import me.xbackpack.galaxysky.enum.player.PlayerStatType
import me.xbackpack.galaxysky.hook.PlaceholderHook
import me.xbackpack.galaxysky.hook.PlaceholderHook.SHOP_LINK
import me.xbackpack.galaxysky.registry.inventory.Inventory
import me.xbackpack.galaxysky.registry.item.Pickaxes
import me.xbackpack.galaxysky.sendMessage
import me.xbackpack.galaxysky.service.FormattingService
import me.xbackpack.galaxysky.service.LocationService
import me.xbackpack.galaxysky.service.PDCService
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDismountEvent
import org.bukkit.event.player.PlayerDropItemEvent
import kotlin.time.Duration.Companion.seconds

object PlayerCommandRegistry {
    private val SPAWN =
        Command.create {
            name = "spawn"
            description = "Teleports you to the server spawn"
            aliases = listOf("stuck")
            requirement = SenderRequirement.PLAYER

            doForPlayer { _, (context) ->
                teleport(LocationService.WORLD_SPAWN)
            }
        }

    private val WORLD =
        Command.create {
            name = "world"
            description = "Teleports the player to the specified world"
            requirement = SenderRequirement.PLAYER

            doForPlayer { _, (context) ->
                sendMessage {
                    text("Please specify a valid world") {
                        colour(NamedTextColor.RED)
                    }
                }
            }

            subcommand("spawn") {
                doForPlayer { _, (context) ->
                    teleport(LocationService.WORLD_SPAWN)
                }
            }

            subcommand("nether") {
                permission = "galaxysky.world.nether"

                doForPlayer { _, (context) ->
                    teleport(LocationService.NETHER_SPAWN)
                }
            }

            subcommand("end") {
                permission = "galaxysky.world.end"

                doForPlayer { _, (context) ->
                    teleport(LocationService.END_SPAWN)
                }
            }

            subcommand("aether") {
                permission = "galaxysky.world.aether"

                doForPlayer { _, (context) ->
                    teleport(LocationService.AETHER_SPAWN)
                }
            }

            subcommand("staff") {
                requirement = SenderRequirement.STAFF

                doForPlayer { _, (context) ->
                    teleport(LocationService.STAFF_WORLD_SPAWN)
                }
            }
        }

    private val AFK =
        Command.create {
            name = "afk"
            description = "Teleports the player to the AFK area"
            requirement = SenderRequirement.PLAYER

            doForPlayer { _, (context) ->
                teleport(LocationService.AFK)
            }
        }

    private val LEADERBOARDS =
        Command.create {
            name = "leaderboards"
            description = "Teleports the player to the leaderboards"
            requirement = SenderRequirement.PLAYER

            doForPlayer { _, (context) ->
                teleport(LocationService.LEADERBOARDS)
            }
        }

    private val APPLY =
        Command.create {
            name = "apply"
            description = "Provides a link to the staff application"
            requirement = SenderRequirement.PLAYER

            doForPlayer { _, (context) ->
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

            doForPlayer { _, (context) ->
                sendMessage {
                    section {
                        text("The GalaxySky IP is:")

                        space()

                        note(PlaceholderHook.SERVER_IP)

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

            doForPlayer { _, (context) ->
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

            doForPlayer { _, (context) ->
                giveItem(Pickaxes.BaysideBeach.STONE_PICKAXE_1)
            }
        }

    private val PLAYTIME =
        Command.create {
            name = "playtime"
            description = "Sends the player their full playtime"
            requirement = SenderRequirement.PLAYER

            doForPlayer { player, (context) ->
                val playtime = PDCService.PlayerData.Stats[player, PlayerStatType.PLAYTIME]

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
                Cooldown(10.seconds, true) { _ ->
                    Message.create {
                        text("You can already drop items!") {
                            colour(NamedTextColor.RED)
                        }
                    }
                }

            name = "drops"
            description = "Allows the player to drop items for 10 seconds"
            aliases = listOf("drop")
            cooldown = cmdCooldown
            requirement = SenderRequirement.PLAYER

            doForPlayer { player, (context) ->
                sendMessage {
                    text("You can now drop items for the next 10 seconds.") {
                        colour(NamedTextColor.GREEN)
                    }
                }

                GalaxySky.runTaskLater(10.seconds) {
                    player.sendMessage(
                        Message.create {
                            text("You can no longer drop items!") {
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
                            text("Use [/drops] to drop items!") {
                                colour(NamedTextColor.RED)
                            }
                        },
                    )
                    event.isCancelled = true
                }
            }
        }

    private val SIT =
        Command.create {
            name = "sit"
            description = "Allows the player to sit down"
            requirement = SenderRequirement.PLAYER
            val enabledPlayers = mutableSetOf<Player>()

            doForPlayer { player, (context) ->
                if (enabledPlayers.contains(player)) {
                    sendMessage {
                        text("You're already sitting!") {
                            colour(NamedTextColor.RED)
                        }
                    }
                } else {
                    spawnEntity<ArmorStand> {
                        isMarker = true
                        isInvisible = true
                        customName(Component.text("Sit"))

                        addPassenger(player)

                        enabledPlayers.add(player)
                    }
                }
            }

            listener<EntityDismountEvent> { event ->
                val player = event.entity as? Player ?: return@listener

                val dismounted = event.dismounted

                val name = dismounted.customName() ?: return@listener

                if (FormattingService.content(name) == "Sit") {
                    player.teleport(player.location.add(0.0, 1.0, 0.0))
                    dismounted.remove()
                    enabledPlayers.remove(player)
                }
            }
        }

    private val TRASH =
        Command.create {
            name = "trash"
            description = "Shows"
            aliases = listOf("disposal", "bin")
            requirement = SenderRequirement.PLAYER

            doForPlayer { _, (context) ->
                showInv(Inventory.new { text("Trash") })
            }
        }

    val commands =
        listOf(
            SPAWN,
            WORLD,
            AFK,
            LEADERBOARDS,
            APPLY,
            IP,
            SHOP,
            START,
            PLAYTIME,
            DROPS,
            SIT,
            TRASH,
        )
}
