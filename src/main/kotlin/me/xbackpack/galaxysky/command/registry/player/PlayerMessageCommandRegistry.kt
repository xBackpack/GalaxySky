package me.xbackpack.galaxysky.command.registry.player

import me.xbackpack.galaxysky.command.api.Command
import me.xbackpack.galaxysky.command.impl.MessageCommand
import me.xbackpack.galaxysky.common.RegistrySupplier
import me.xbackpack.galaxysky.hook.PlaceholderHook
import me.xbackpack.galaxysky.message.Message
import net.kyori.adventure.text.format.NamedTextColor

object PlayerMessageCommandRegistry : RegistrySupplier<Command> {
    private const val DISCORD_LINK = PlaceholderHook.DISCORD
    private const val SERVER_IP = PlaceholderHook.SERVER_IP
    private const val SHOP_LINK = PlaceholderHook.SHOP_LINK

    override fun get(): List<Command> {
        val applyCmd =
            MessageCommand.create {
                name = "apply"
                description = "Provides a link to the staff application"

                message =
                    Message.create {
                        text("Apply on our discord!")

                        newline()

                        text("Link:")

                        space()

                        link(DISCORD_LINK)

                        colour(NamedTextColor.LIGHT_PURPLE)
                    }
            }

        val ipCmd =
            MessageCommand.create {
                name = "ip"
                description = "Provides the IP of the server"
                aliases = listOf("serverip")

                message =
                    Message.create {
                        text("The GalaxySky IP is:")

                        space()

                        snippet(SERVER_IP)

                        colour(NamedTextColor.LIGHT_PURPLE)
                    }
            }

        val shopCmd =
            MessageCommand.create {
                name = "shop"
                description = "Provides a link to the GalaxySky webstore"
                aliases = listOf("store", "webstore")

                message =
                    Message.create {
                        text("The GalaxySky shop link is:")

                        space()

                        link(SHOP_LINK)

                        colour(NamedTextColor.LIGHT_PURPLE)
                    }
            }

        return listOf(applyCmd, ipCmd, shopCmd)
    }
}
