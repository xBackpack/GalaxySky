package me.xbackpack.galaxysky.service

import io.papermc.paper.event.player.AsyncChatEvent
import me.xbackpack.galaxysky.GalaxySky
import me.xbackpack.galaxysky.common.Registry
import me.xbackpack.galaxysky.hook.PlaceholderHook
import me.xbackpack.galaxysky.message.Message
import me.xbackpack.galaxysky.message.sendMessage
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit

object ChatService : Registry {
    private val chatMessages =
        setOf(
            Message.create {
                newline()

                text("You are currently playing on ") {
                    colour(NamedTextColor.LIGHT_PURPLE)
                }

                snippet(PlaceholderHook.SERVER_IP)

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
        Bukkit.broadcast(chatMessages.random().root)
    }

    override fun init() {
        ListenerService.hookEvent<AsyncChatEvent> { event ->
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
                    finalMessage = MiniMessage.miniMessage().deserialize(FormattingService.legacyFormat(message))
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
                    }.root
            }
        }
    }
}
