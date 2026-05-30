package me.xbackpack.galaxysky.registry.command

import com.mojang.brigadier.arguments.IntegerArgumentType
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver
import me.xbackpack.galaxysky.api.old.data.Command
import me.xbackpack.galaxysky.enum.command.SenderRequirement
import me.xbackpack.galaxysky.registry.item.Pickaxes
import me.xbackpack.galaxysky.service.LocationService
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.GameMode
import org.bukkit.entity.Player

object StaffCommandRegistry {
    private val ADMIN_PICKAXE =
        Command.create(
            "adminpickaxe",
            "Gives the player the starter pickaxe",
            SenderRequirement.STAFF(),
        ) {
            doForPlayer { _, _ ->
                giveItem(Pickaxes.ADMIN_PICKAXE)
            }
        }

    private val AREA =
        Command.create(
            "area",
            "Teleports the player to the specified area",
            SenderRequirement.STAFF(),
        ) {
            doForPlayer { _, _ ->
                sendMessage {
                    text("Please specify a valid world") {
                        colour(NamedTextColor.RED)
                    }
                }
            }

            subcommand("staff", SenderRequirement.STAFF_OR_PERMISSION("galaxysky.area.staff")) {
                doForPlayer { _, _ ->
                    teleport(LocationService.STAFF_AREA)
                }
            }

            subcommand("builder", SenderRequirement.STAFF_OR_PERMISSION("galaxysky.area.builder")) {
                doForPlayer { _, _ ->
                    teleport(LocationService.BUILDER_AREA)
                }
            }
        }

    private val GMC =
        Command.create(
            "gmc",
            "Updates the player's game mode to creative",
            SenderRequirement.STAFF(),
        ) {
            aliases = listOf("creative")

            optional("player", SenderRequirement.STAFF(), ArgumentTypes.player()) {
                doForPlayer { _, getter ->
                    val target = getter.extractAndResolveFirst<PlayerSelectorArgumentResolver, Player>("player")

                    sendMessage {
                        text("You updated ${target.name}'s game to creative!") {
                            colour(NamedTextColor.GREEN)
                        }
                    }

                    sendMessage(target) {
                        text("Your gamemode has been updated to creative!") {
                            colour(NamedTextColor.GREEN)
                        }
                    }

                    updateGameMode(GameMode.CREATIVE, target)
                }
            }

            doForPlayer { _, _ ->
                sendMessage {
                    text("Your gamemode has been updated to creative!") {
                        colour(NamedTextColor.GREEN)
                    }
                }

                updateGameMode(GameMode.CREATIVE)
            }
        }

    private val GMS =
        Command.create(
            "gms",
            "Updates the players game mode to survival",
            SenderRequirement.STAFF(),
        ) {
            aliases = listOf("survival")

            optional("player", SenderRequirement.STAFF(), ArgumentTypes.player()) {
                doForPlayer { _, getter ->
                    val target = getter.extractAndResolveFirst<PlayerSelectorArgumentResolver, Player>("player")

                    sendMessage {
                        text("You updated ${target.name}'s game to survival!") {
                            colour(NamedTextColor.GREEN)
                        }
                    }

                    sendMessage(target) {
                        text("Your gamemode has been updated to survival!") {
                            colour(NamedTextColor.GREEN)
                        }
                    }

                    updateGameMode(GameMode.SURVIVAL, target)
                }
            }

            doForPlayer { _, _ ->
                sendMessage {
                    text("Your gamemode has been updated to survival!") {
                        colour(NamedTextColor.GREEN)
                    }
                }

                updateGameMode(GameMode.SURVIVAL)
            }
        }

    private val GMSP =
        Command.create(
            "gmsp",
            "Updates the players game mode to spectator",
            SenderRequirement.STAFF(),
        ) {
            aliases = listOf("spectator")

            optional("player", SenderRequirement.STAFF(), ArgumentTypes.player()) {
                doForPlayer { _, getter ->
                    val target = getter.extractAndResolveFirst<PlayerSelectorArgumentResolver, Player>("player")

                    sendMessage {
                        text("You updated ${target.name}'s game to spectator!") {
                            colour(NamedTextColor.GREEN)
                        }
                    }

                    sendMessage(target) {
                        text("Your gamemode has been updated to spectator!") {
                            colour(NamedTextColor.GREEN)
                        }
                    }

                    updateGameMode(GameMode.SPECTATOR, target)
                }
            }

            doForPlayer { _, _ ->
                sendMessage {
                    text("Your gamemode has been updated to spectator!") {
                        colour(NamedTextColor.GREEN)
                    }
                }

                updateGameMode(GameMode.SPECTATOR)
            }
        }

    private val GMA =
        Command.create(
            "gma",
            "Updates the player's game mode to adventure",
            SenderRequirement.STAFF(),
        ) {
            aliases = listOf("adventure")

            optional("player", SenderRequirement.STAFF(), ArgumentTypes.player()) {
                doForPlayer { _, getter ->
                    val target = getter.extractAndResolveFirst<PlayerSelectorArgumentResolver, Player>("player")

                    sendMessage {
                        text("You updated ${target.name}'s game to adventure!") {
                            colour(NamedTextColor.GREEN)
                        }
                    }

                    sendMessage(target) {
                        text("Your gamemode has been updated to adventure!") {
                            colour(NamedTextColor.GREEN)
                        }
                    }

                    updateGameMode(GameMode.ADVENTURE, target)
                }
            }

            doForPlayer { _, _ ->
                sendMessage {
                    text("Your gamemode has been updated to adventure!") {
                        colour(NamedTextColor.GREEN)
                    }
                }

                updateGameMode(GameMode.ADVENTURE)
            }
        }

    private val FLYSPEED =
        Command.create(
            "flyspeed",
            "Controls the player's flying speed",
            SenderRequirement.PERMISSION("galaxysky.command.fly"),
        ) {
            argument("speed", IntegerArgumentType.integer(-10, 10))

            doForPlayer { _, getter ->
                val speed = getter.extract<Int>("speed")

                sendMessage {
                    text("You are now flying at ${speed}x speed!") {
                        colour(NamedTextColor.GREEN)
                    }
                }

                updateFlyingSpeed(speed)
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
            FLYSPEED,
        )
}
