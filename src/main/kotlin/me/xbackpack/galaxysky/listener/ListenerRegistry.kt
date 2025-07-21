package me.xbackpack.galaxysky.listener

import io.papermc.paper.event.player.AsyncChatEvent
import me.xbackpack.galaxysky.GalaxySky
import me.xbackpack.galaxysky.common.Registry
import me.xbackpack.galaxysky.item.api.giveItem
import me.xbackpack.galaxysky.item.registry.Pickaxes
import me.xbackpack.galaxysky.message.Message
import me.xbackpack.galaxysky.message.content
import me.xbackpack.galaxysky.message.sendMessage
import me.xbackpack.galaxysky.service.LocationService
import me.xbackpack.galaxysky.service.LuckPermsService
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object ListenerRegistry : Registry {
    override fun init() {
        hookEvent<PlayerJoinEvent> { event ->
            val player = event.player

            event.joinMessage(
                Message
                    .create {
                        section {
                            text("[")

                            text("+") {
                                colour(NamedTextColor.GREEN)
                            }

                            text("]")

                            colour(NamedTextColor.DARK_GRAY)
                        }

                        space()

                        text(player.name) {
                            colour(NamedTextColor.GRAY)
                        }
                    }.root,
            )

            if (!player.hasPlayedBefore()) {
                event.joinMessage(
                    Message
                        .create {
                            section {
                                text("Welcome,")

                                space()

                                text(player.name) {
                                    colour(NamedTextColor.LIGHT_PURPLE)
                                }

                                text(", to GalaxySky!")

                                space()

                                text("(")

                                text("#${Bukkit.getOfflinePlayers().size}") {
                                    colour(NamedTextColor.YELLOW)
                                }

                                text(")")

                                colour(NamedTextColor.GRAY)
                            }
                        }.root,
                )

                val item = Pickaxes.STONE_PICKAXE_1

                player.giveItem(item)
            }

            player.teleport(LocationService.spawnLocation)
        }

        hookEvent<PlayerQuitEvent> { event ->
            val player = event.player

            event.quitMessage(
                Message
                    .create {
                        section {
                            text("[")

                            text("-") {
                                colour(NamedTextColor.RED)
                            }

                            text("]")

                            colour(NamedTextColor.DARK_GRAY)
                        }

                        space()

                        text(player.name) {
                            colour(NamedTextColor.GRAY)
                        }
                    }.root,
            )
        }

        hookEvent<AsyncChatEvent> { event ->
            val player = event.player

            if (GalaxySky.chatMuted && !LuckPermsService.hasPermission(player, "galaxysky.chat.exempt")) {
                player.sendMessage(
                    Message.create {
                        text("You do not have permission to chat at this time") {
                            colour(NamedTextColor.RED)
                        }
                    },
                )

                event.isCancelled = true

                return@hookEvent
            }

            event.renderer { _, displayName, message, _ ->
                val primaryGroupName = LuckPermsService.getPrimaryGroupName(player)

                val prefix = LuckPermsService.getPrefix(player)
                val suffix = LuckPermsService.getSuffix(player)

                var finalMessage = message

                if (LuckPermsService.isStaff(player)) {
                    finalMessage = MiniMessage.miniMessage().deserialize(message.content())
                }

                Message
                    .create {
                        componentFromLegacyColourCodes(prefix)

                        component(displayName.colorIfAbsent(LuckPermsService.getNameColour(primaryGroupName)))

                        componentFromLegacyColourCodes(suffix)

                        space()

                        text("»") {
                            colour(NamedTextColor.DARK_GRAY)
                        }

                        space()

                        section {
                            component(finalMessage)

                            colour(
                                if (primaryGroupName == "default") NamedTextColor.GRAY else NamedTextColor.WHITE,
                            )
                        }
                    }.root
            }
        }
    }

    inline fun <reified T : Event> hookEvent(
        priority: EventPriority = EventPriority.NORMAL,
        ignoreCancelled: Boolean = false,
        crossinline handler: (T) -> Unit,
    ) {
        val listener = object : Listener {}

        GalaxySky.pluginManager.registerEvent(
            T::class.java,
            listener,
            priority,
            { _, event ->
                (event as? T)?.let { handler(it) }
            },
            GalaxySky.instance,
            ignoreCancelled,
        )
    }
}
