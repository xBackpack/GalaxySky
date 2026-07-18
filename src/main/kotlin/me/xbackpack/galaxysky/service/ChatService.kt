package me.xbackpack.galaxysky.service

import io.papermc.paper.event.player.AsyncChatEvent
import me.xbackpack.galaxysky.GalaxySky
import me.xbackpack.galaxysky.api.util.message
import me.xbackpack.galaxysky.api.util.msg
import me.xbackpack.galaxysky.enum.Colour
import me.xbackpack.galaxysky.hook.PlaceholderHook
import me.xbackpack.galaxysky.service.FormattingService.legacyFormat
import me.xbackpack.galaxysky.service.LuckPermsService.getPrefix
import me.xbackpack.galaxysky.service.LuckPermsService.getPrimaryGroupName
import me.xbackpack.galaxysky.service.LuckPermsService.getSuffix
import me.xbackpack.galaxysky.service.LuckPermsService.isStaff
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit

object ChatService {
    private val chatMessages =
        setOf(
            message {
                newline()

                text("You are currently playing on ") {
                    colour(Colour.LIGHT_PURPLE)
                }

                note(PlaceholderHook.SERVER_IP)

                newline()
            },
            message {
                newline()

                text("Make sure to visit our webstore at ") {
                    colour(Colour.LIGHT_PURPLE)
                }

                link(PlaceholderHook.SHOP_LINK)

                newline()
            },
            message {

                newline()

                text("Make sure to join our discord at ") {
                    colour(Colour.LIGHT_PURPLE)
                }

                link(PlaceholderHook.DISCORD)

                newline()
            },
        )

    fun sendRandomMessage() {
        Bukkit.broadcast(chatMessages.random().build())
    }

    fun init() {
        ListenerService.hookEvent<AsyncChatEvent> { event ->
            val player = event.player

            if (GalaxySky.chatMuted && !player.hasPermission("galaxysky.chat.exempt")) {
                player.msg {
                    text("You do not have permission to chat at this time") {
                        colour(Colour.RED)
                    }
                }

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

                message {
                    componentFromLegacyString(prefix)

                    component(displayName.colorIfAbsent(LuckPermsService.getNameColour(primaryGroupName)))

                    componentFromLegacyString(suffix)

                    space()

                    text("»") {
                        colour(Colour.DARK_GREY)
                    }

                    space()

                    section {
                        component(finalMessage)

                        colour(
                            if (primaryGroupName == "default") Colour.GREY else Colour.WHITE,
                        )
                    }
                }.build()
            }
        }
    }
}
