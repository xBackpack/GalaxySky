package me.xbackpack.galaxysky.registry.command

import me.xbackpack.galaxysky.GalaxySky
import me.xbackpack.galaxysky.api.command.Cooldown
import me.xbackpack.galaxysky.api.command.Permission
import me.xbackpack.galaxysky.api.command.Requirement
import me.xbackpack.galaxysky.api.util.command
import me.xbackpack.galaxysky.api.util.getStat
import me.xbackpack.galaxysky.api.util.giveItem
import me.xbackpack.galaxysky.api.util.message
import me.xbackpack.galaxysky.api.util.msg
import me.xbackpack.galaxysky.api.util.showInventory
import me.xbackpack.galaxysky.api.util.spawnEntity
import me.xbackpack.galaxysky.enum.Colour
import me.xbackpack.galaxysky.enum.player.PlayerStatType
import me.xbackpack.galaxysky.hook.PlaceholderHook
import me.xbackpack.galaxysky.hook.PlaceholderHook.SHOP_LINK
import me.xbackpack.galaxysky.registry.inventory.Inventories
import me.xbackpack.galaxysky.registry.item.Pickaxes
import me.xbackpack.galaxysky.service.FormattingService
import me.xbackpack.galaxysky.service.FormattingService.content
import me.xbackpack.galaxysky.service.LocationService
import net.kyori.adventure.text.Component
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDismountEvent
import org.bukkit.event.player.PlayerDropItemEvent
import kotlin.time.Duration.Companion.seconds

object PlayerCommandRegistry {
    private val SPAWN =
        command(
            name = "spawn",
            description = "Teleports you to the server spawn",
            requirement = Requirement.PLAYER,
            aliases = listOf("stuck"),
        ) {
            doForPlayer { player, _ ->
                player.teleport(LocationService.WORLD_SPAWN)
            }
        }

    private val WORLD =
        command(
            name = "world",
            description = "Teleports the player to the specified world",
            requirement = Requirement.PLAYER,
        ) {
            doForPlayer { player, _ ->
                player.msg {
                    text("Please specify a valid world") {
                        colour(Colour.RED)
                    }
                }
            }

            subcommand(
                name = "spawn",
            ) {
                doForPlayer { player, _ ->
                    player.teleport(LocationService.WORLD_SPAWN)
                }
            }

            subcommand(
                name = "nether",
                permission = Permission("galaxysky.world.nether"),
            ) {
                doForPlayer { player, _ ->
                    player.teleport(LocationService.NETHER_SPAWN)
                }
            }

            subcommand(
                name = "end",
                permission = Permission("galaxysky.world.end"),
            ) {
                doForPlayer { player, _ ->
                    player.teleport(LocationService.END_SPAWN)
                }
            }

            subcommand(
                name = "aether",
                permission = Permission("galaxysky.world.aether"),
            ) {
                doForPlayer { player, _ ->
                    player.teleport(LocationService.AETHER_SPAWN)
                }
            }

            subcommand(
                name = "staff",
                requirement = Requirement.STAFF,
            ) {
                doForPlayer { player, _ ->
                    player.teleport(LocationService.STAFF_WORLD_SPAWN)
                }
            }
        }

    private val AFK =
        command(
            name = "afk",
            description = "Teleports the player to the AFK area",
            requirement = Requirement.PLAYER,
        ) {
            doForPlayer { player, _ ->
                player.teleport(LocationService.AFK)
            }
        }

    private val LEADERBOARDS =
        command(
            name = "leaderboards",
            description = "Teleports the player to the leaderboards",
            requirement = Requirement.PLAYER,
        ) {
            doForPlayer { player, _ ->
                player.teleport(LocationService.LEADERBOARDS)
            }
        }

    private val APPLY =
        command(
            name = "apply",
            description = "Provides a link to the staff application",
            requirement = Requirement.PLAYER,
        ) {
            doForPlayer { player, _ ->
                player.msg {
                    section {
                        text("Apply on our discord!")

                        newline()

                        text("Link:")

                        space()

                        link(PlaceholderHook.DISCORD)

                        colour(Colour.LIGHT_PURPLE)
                    }
                }
            }
        }

    private val IP =
        command(
            name = "ip",
            description = "Provides the IP of the server",
            requirement = Requirement.PLAYER,
            aliases = listOf("serverip"),
        ) {
            doForPlayer { player, _ ->
                player.msg {
                    section {
                        text("The GalaxySky IP is:")

                        space()

                        note(PlaceholderHook.SERVER_IP)

                        colour(Colour.LIGHT_PURPLE)
                    }
                }
            }
        }

    private val SHOP =
        command(
            name = "shop",
            description = "Provides a link to the GalaxySky webstore",
            requirement = Requirement.PLAYER,
            aliases = listOf("store", "webstore"),
        ) {
            doForPlayer { player, _ ->
                player.msg {
                    section {
                        text("The GalaxySky shop link is:")
                        space()

                        link(SHOP_LINK)

                        colour(Colour.LIGHT_PURPLE)
                    }
                }
            }
        }

    private val START =
        command(
            name = "start",
            description = "Gives the player the starter pickaxe",
            requirement = Requirement.PLAYER,
            aliases = listOf("begin"),
            cooldown = Cooldown(60.seconds),
        ) {
            doForPlayer { player, _ ->
                player.giveItem(Pickaxes.WOODEN_PICKAXE_1)
            }
        }

    private val PLAYTIME =
        command(
            name = "playtime",
            description = "Sends the player their full playtime",
            requirement = Requirement.PLAYER,
        ) {
            doForPlayer { player, _ ->
                val playtime = player.getStat(PlayerStatType.PLAYTIME)

                player.msg {
                    section {
                        text("You have ")

                        text(FormattingService.shortenTimeFull(playtime))

                        text(" of playtime!")

                        colour(Colour.GREEN)
                    }
                }
            }
        }

    private val DROPS =
        command(
            name = "drops",
            description = "Allows the player to drop items for 10 seconds",
            requirement = Requirement.PLAYER,
            aliases = listOf("drop"),
            cooldown =
                Cooldown(10.seconds, true) { _ ->
                    message {
                        text("You can already drop items!") { colour(Colour.RED) }
                    }
                },
        ) {
            doForPlayer { player, _ ->
                player.msg {
                    text("You can now drop items for the next 10 seconds.") { colour(Colour.GREEN) }
                }

                GalaxySky.runTaskLater(10.seconds) {
                    player.msg {
                        text("You can no longer drop items!") { colour(Colour.RED) }
                    }
                }
            }

            listener<PlayerDropItemEvent> { event ->
                val player = event.player

                if (!(cooldown ?: return@listener).isOnCooldown(player)) {
                    player.msg {
                        text("Use [/drops] to drop items!") {
                            colour(Colour.RED)
                        }
                    }

                    event.isCancelled = true
                }
            }
        }

    private val SIT =
        command(
            name = "sit",
            description = "Allows the player to sit down",
            requirement = Requirement.PLAYER,
        ) {
            val enabledPlayers = mutableSetOf<Player>()

            doForPlayer { player, _ ->
                if (enabledPlayers.contains(player)) {
                    player.msg {
                        text("You're already sitting!") {
                            colour(Colour.RED)
                        }
                    }
                } else {
                    player.location.spawnEntity<ArmorStand> {
                        isMarker = true
                        isInvisible = true
                        customName(Component.text("Sit"))

                        addPassenger(player)
                    }

                    enabledPlayers.add(player)
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
        command(
            name = "trash",
            description = "Opens the bin",
            requirement = Requirement.PLAYER,
            aliases = listOf("disposal", "bin"),
        ) {
            doForPlayer { player, _ ->
                player.showInventory(Inventories.BIN)
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
