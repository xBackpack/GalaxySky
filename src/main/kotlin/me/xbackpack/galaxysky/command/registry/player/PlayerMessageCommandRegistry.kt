package me.xbackpack.galaxysky.command.registry.player

import me.xbackpack.galaxysky.command.impl.BaseCommand
import me.xbackpack.galaxysky.command.impl.MessageCommand
import me.xbackpack.galaxysky.hook.PlaceholderHook
import me.xbackpack.galaxysky.message.Message
import net.kyori.adventure.text.format.NamedTextColor

object PlayerMessageCommandRegistry {
    private const val DISCORD_LINK = PlaceholderHook.DISCORD
    private const val SERVER_IP = PlaceholderHook.SERVER_IP
    private const val SHOP_LINK = PlaceholderHook.SHOP_LINK

    val commands =
        listOf(
            BaseCommand.create({ MessageCommand() }) {
                name = "apply"
                description = "Provides a link to the staff application"

                message =
                    Message.create {
                        section {
                            text("Apply on our discord!")

                            newline()

                            text("Link:")

                            space()

                            link(DISCORD_LINK)

                            colour(NamedTextColor.LIGHT_PURPLE)
                        }
                    }
            },
            BaseCommand.create({ MessageCommand() }) {
                name = "ip"
                description = "Provides the IP of the server"
                aliases = listOf("serverip")

                message =
                    Message.create {
                        section {
                            text("The GalaxySky IP is:")

                            space()

                            snippet(SERVER_IP)

                            colour(NamedTextColor.LIGHT_PURPLE)
                        }
                    }
            },
            BaseCommand.create({ MessageCommand() }) {
                name = "shop"
                description = "Provides a link to the GalaxySky webstore"
                aliases = listOf("store", "webstore")

                message =
                    Message.create {
                        section {
                            text("The GalaxySky shop link is:")
                            space()

                            link(SHOP_LINK)

                            colour(NamedTextColor.LIGHT_PURPLE)
                        }
                    }
            },
        )
}
