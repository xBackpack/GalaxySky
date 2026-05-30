package me.xbackpack.galaxysky.service

import io.papermc.paper.event.player.AsyncChatEvent
import me.xbackpack.galaxysky.GalaxySky
import me.xbackpack.galaxysky.api.message.Message
import me.xbackpack.galaxysky.hook.PlaceholderHook
import me.xbackpack.galaxysky.sendMessage
import me.xbackpack.galaxysky.service.FormattingService.legacyFormat
import me.xbackpack.galaxysky.service.LuckPermsService.getPrefix
import me.xbackpack.galaxysky.service.LuckPermsService.getPrimaryGroupName
import me.xbackpack.galaxysky.service.LuckPermsService.getSuffix
import me.xbackpack.galaxysky.service.LuckPermsService.isStaff
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit

object ChatService {
    private val chatMessages =
        setOf(
            Message.create {
                newline()

                text("You are currently playing on ") {
                    colour(NamedTextColor.LIGHT_PURPLE)
                }

                note(PlaceholderHook.SERVER_IP)

                newline()
            },
            Message.create {

                newline()

                text("Make sure to visit our webstore at ") {
                    colour(NamedTextColor.LIGHT_PURPLE)
                }

                link(PlaceholderHook.SHOP_LINK)

                newline()
            },
            Message.create {

                newline()

                text("Make sure to join our discord at ") {
                    colour(NamedTextColor.LIGHT_PURPLE)
                }

                link(PlaceholderHook.DISCORD)

                newline()
            },
        )

    fun sendRandomMessage() {
        Bukkit.broadcast(chatMessages.random().component)
    }

    fun init() {
        ListenerService.hookEvent<AsyncChatEvent> { event ->
            val player = event.player

            if (GalaxySky.chatMuted && !player.hasPermission("galaxysky.chat.exempt")) {
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
                val primaryGroupName = player.getPrimaryGroupName()

                val prefix = player.getPrefix()
                val suffix = player.getSuffix()

                var finalMessage = message

                if (player.isStaff()) {
                    finalMessage = MiniMessage.miniMessage().deserialize(message.legacyFormat())
                }

                Message
                    .create {
                        componentFromLegacyString(prefix)

                        component(displayName.colorIfAbsent(LuckPermsService.getNameColour(primaryGroupName)))

                        componentFromLegacyString(suffix)

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
                    }.component
            }
        }
    }
}
