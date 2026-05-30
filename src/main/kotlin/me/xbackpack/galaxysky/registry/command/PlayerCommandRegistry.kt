package me.xbackpack.galaxysky.registry.command

import me.xbackpack.galaxysky.GalaxySky
import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.api.old.common.Cooldown
import me.xbackpack.galaxysky.api.old.data.Command
import me.xbackpack.galaxysky.enum.command.SenderRequirement
import me.xbackpack.galaxysky.enum.player.PlayerStatType
import me.xbackpack.galaxysky.hook.PlaceholderHook
import me.xbackpack.galaxysky.hook.PlaceholderHook.SHOP_LINK
import me.xbackpack.galaxysky.registry.inventory.Inventory
import me.xbackpack.galaxysky.registry.item.Pickaxes
import me.xbackpack.galaxysky.sendMessage
import me.xbackpack.galaxysky.service.FormattingService
import me.xbackpack.galaxysky.service.FormattingService.content
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
        Command.create(
            "spawn",
            "Teleports you to the server spawn",
            SenderRequirement.PLAYER(),
        ) {
            aliases = listOf("stuck")

            doForPlayer { _, _ ->
                teleport(LocationService.WORLD_SPAWN)
            }
        }

    private val WORLD =
        Command.create(
            "world",
            "Teleports the player to the specified world",
            SenderRequirement.PLAYER(),
        ) {
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

            subcommand(
                "nether",
                SenderRequirement.PERMISSION("galaxysky.world.nether"),
            ) {
                doForPlayer { _, _ ->
                    teleport(LocationService.NETHER_SPAWN)
                }
            }

            subcommand(
                "end",
                SenderRequirement.PERMISSION("galaxysky.world.end"),
            ) {
                doForPlayer { _, _ ->
                    teleport(LocationService.END_SPAWN)
                }
            }

            subcommand(
                "aether",
                SenderRequirement.PERMISSION("galaxysky.world.aether"),
            ) {
                doForPlayer { _, _ ->
                    teleport(LocationService.AETHER_SPAWN)
                }
            }

            subcommand(
                "staff",
                SenderRequirement.STAFF(),
            ) {
                doForPlayer { _, _ ->
                    teleport(LocationService.STAFF_WORLD_SPAWN)
                }
            }
        }

    private val AFK =
        Command.create(
            "afk",
            "Teleports the player to the AFK area",
            SenderRequirement.PLAYER(),
        ) {
            doForPlayer { _, _ ->
                teleport(LocationService.AFK)
            }
        }

    private val LEADERBOARDS =
        Command.create(
            "leaderboards",
            "Teleports the player to the leaderboards",
            SenderRequirement.PLAYER(),
        ) {
            doForPlayer { _, _ ->
                teleport(LocationService.LEADERBOARDS)
            }
        }

    private val APPLY =
        Command.create(
            "apply",
            "Provides a link to the staff application",
            SenderRequirement.PLAYER(),
        ) {
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
        Command.create(
            "ip",
            "Provides the IP of the server",
            SenderRequirement.PLAYER(),
        ) {
            aliases = listOf("serverip")

            doForPlayer { _, _ ->
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
        Command.create(
            "shop",
            "Provides a link to the GalaxySky webstore",
            SenderRequirement.PLAYER(),
        ) {
            aliases = listOf("store", "webstore")

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
        Command.create(
            "start",
            "Gives the player the starter pickaxe",
            SenderRequirement.PLAYER(),
        ) {
            aliases = listOf("begin")
            cooldown = Cooldown(60.seconds)

            doForPlayer { _, _ ->
                giveItem(Pickaxes.BaysideBeach.STONE_PICKAXE_1)
            }
        }

    private val PLAYTIME =
        Command.create(
            "playtime",
            "Sends the player their full playtime",
            SenderRequirement.PLAYER(),
        ) {
            doForPlayer { player, _ ->
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
        Command.create(
            "drops",
            "Allows the player to drop items for 10 seconds",
            SenderRequirement.PLAYER(),
        ) {
            val cooldownObject =
                Cooldown(10.seconds, true) { _ ->
                    Message.create {
                        text("You can already drop items!") { colour(NamedTextColor.RED) }
                    }
                }

            aliases = listOf("drop")
            cooldown = cooldownObject

            doForPlayer { player, _ ->
                sendMessage {
                    text("You can now drop items for the next 10 seconds.") { colour(NamedTextColor.GREEN) }
                }

                GalaxySky.runTaskLater(10.seconds) {
                    player.sendMessage(
                        Message.create {
                            text("You can no longer drop items!") { colour(NamedTextColor.RED) }
                        },
                    )
                }
            }

            listener<PlayerDropItemEvent> { event ->
                val player = event.player

                if (!cooldownObject.isOnCooldown(player)) {
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
        Command.create(
            "sit",
            "Allows the player to sit down",
            SenderRequirement.PLAYER(),
        ) {
            val enabledPlayers = mutableSetOf<Player>()

            doForPlayer { player, _ ->
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

                if (!enabledPlayers.contains(player)) return@listener

                val dismounted = event.dismounted

                val name = dismounted.customName() ?: return@listener

                if (name.content() == "Sit") {
                    player.teleport(player.location.add(0.0, 1.0, 0.0))
                    dismounted.remove()
                    enabledPlayers.remove(player)
                }
            }
        }

    private val TRASH =
        Command.create(
            "trash",
            "Opens the bin",
            SenderRequirement.PLAYER(),
        ) {
            aliases = listOf("disposal", "bin")

            doForPlayer { _, _ ->
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
