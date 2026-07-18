package me.xbackpack.galaxysky.registry.command

import com.mojang.brigadier.arguments.FloatArgumentType
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver
import me.xbackpack.galaxysky.api.command.Permission
import me.xbackpack.galaxysky.api.command.Requirement
import me.xbackpack.galaxysky.api.util.command
import me.xbackpack.galaxysky.api.util.extract
import me.xbackpack.galaxysky.api.util.extractAndResolveFirst
import me.xbackpack.galaxysky.api.util.get
import me.xbackpack.galaxysky.api.util.giveItem
import me.xbackpack.galaxysky.api.util.msg
import me.xbackpack.galaxysky.api.util.updateGameMode
import me.xbackpack.galaxysky.enum.Colour
import me.xbackpack.galaxysky.registry.item.Pickaxes
import me.xbackpack.galaxysky.service.LocationService
import org.bukkit.GameMode
import org.bukkit.entity.Player

object StaffCommandRegistry {
    private val ADMIN_PICKAXE =
        command(
            name = "adminpickaxe",
            description = "Gives the player the starter pickaxe",
            requirement = Requirement.STAFF,
        ) {
            doForPlayer { player, _ ->
                player.giveItem(Pickaxes.ADMIN_PICKAXE)
            }
        }

    private val AREA =
        command(
            name = "area",
            description = "Teleports the player to the specified area",
            requirement = Requirement.STAFF,
        ) {
            doForPlayer { player, _ ->
                player.msg {
                    text("Please specify a valid world") {
                        colour(Colour.RED)
                    }
                }
            }

            subcommand(
                name = "staff",
                permission = Permission("galaxysky.area.staff"),
            ) {
                doForPlayer { player, _ ->
                    player.teleport(LocationService.STAFF_AREA)
                }
            }

            subcommand(
                name = "builder",
                permission = Permission("galaxysky.area.builder"),
            ) {
                doForPlayer { player, _ ->
                    player.teleport(LocationService.BUILDER_AREA)
                }
            }
        }

    private val GMC =
        command(
            name = "gmc",
            description = "Updates the player's game mode to creative",
            requirement = Requirement.STAFF,
            aliases = listOf("creative"),
        ) {
            doForPlayer { player, _ ->
                player.updateGameMode(GameMode.CREATIVE)
            }

            optional("player", ArgumentTypes.player(), Requirement.STAFF_OR_CONSOLE) {
                doForPlayer { player, args ->
                    val target = args["player"].extractAndResolveFirst<PlayerSelectorArgumentResolver, Player>()

                    target.updateGameMode(GameMode.CREATIVE, player)
                }

                doForConsole { sender, args ->
                    val target = args["player"].extractAndResolveFirst<PlayerSelectorArgumentResolver, Player>()

                    target.updateGameMode(GameMode.CREATIVE, sender)
                }
            }
        }

    private val GMS =
        command(
            name = "gms",
            description = "Updates the player's game mode to survival",
            requirement = Requirement.STAFF,
            aliases = listOf("survival"),
        ) {
            doForPlayer { player, _ ->
                player.updateGameMode(GameMode.SURVIVAL)
            }

            optional("player", ArgumentTypes.player(), Requirement.STAFF_OR_CONSOLE) {
                doForPlayer { player, args ->
                    val target = args["player"].extractAndResolveFirst<PlayerSelectorArgumentResolver, Player>()

                    target.updateGameMode(GameMode.SURVIVAL, player)
                }

                doForConsole { sender, args ->
                    val target = args["player"].extractAndResolveFirst<PlayerSelectorArgumentResolver, Player>()

                    target.updateGameMode(GameMode.SURVIVAL, sender)
                }
            }
        }

    private val GMSP =
        command(
            name = "gmsp",
            description = "Updates the player's game mode to spectator",
            requirement = Requirement.STAFF,
            aliases = listOf("spectator"),
        ) {
            doForPlayer { player, _ ->
                player.updateGameMode(GameMode.SPECTATOR)
            }

            optional("player", ArgumentTypes.player(), Requirement.STAFF_OR_CONSOLE) {
                doForPlayer { player, args ->
                    val target = args["player"].extractAndResolveFirst<PlayerSelectorArgumentResolver, Player>()

                    target.updateGameMode(GameMode.SPECTATOR, player)
                }

                doForConsole { sender, args ->
                    val target = args["player"].extractAndResolveFirst<PlayerSelectorArgumentResolver, Player>()

                    target.updateGameMode(GameMode.SPECTATOR, sender)
                }
            }
        }

    private val GMA =
        command(
            name = "gma",
            description = "Updates the player's game mode to adventure",
            requirement = Requirement.STAFF,
            aliases = listOf("adventure"),
        ) {
            doForPlayer { player, _ ->
                player.updateGameMode(GameMode.ADVENTURE)
            }

            optional("player", ArgumentTypes.player(), Requirement.STAFF_OR_CONSOLE) {
                doForPlayer { player, args ->
                    val target = args["player"].extractAndResolveFirst<PlayerSelectorArgumentResolver, Player>()

                    target.updateGameMode(GameMode.ADVENTURE, player)
                }

                doForConsole { sender, args ->
                    val target = args["player"].extractAndResolveFirst<PlayerSelectorArgumentResolver, Player>()

                    target.updateGameMode(GameMode.ADVENTURE, sender)
                }
            }
        }

    private val FLY_SPEED =
        command(
            name = "flyspeed",
            description = "Controls the player's flying speed",
            requirement = Requirement.STAFF,
            permission = Permission("galaxysky.command.fly"),
        ) {
            argument("speed", FloatArgumentType.floatArg(-10f, 10f))

            doForPlayer { player, args ->
                val speed = args["speed"].extract<Float>()

                player.msg {
                    text("You are now flying at ${speed}x speed!") {
                        colour(Colour.GREEN)
                    }
                }

                player.flySpeed = speed / 10
            }
        }

    private val CATALOG =
        command(
            name = "catalog",
            description = "The catalog of items",
            requirement = Requirement.STAFF,
        ) {
            doForPlayer { _, _ ->
                TODO("Make inventory API?")
                TODO("OR just expand Inventory class")
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
            FLY_SPEED,
            CATALOG,
        )
}
