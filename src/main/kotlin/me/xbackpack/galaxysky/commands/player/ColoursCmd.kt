package me.xbackpack.galaxysky.commands.player

import me.xbackpack.galaxysky.commands.commandTypes.MessageCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration

class ColoursCmd : MessageCommand {
    companion object {
        private val msg =
            Component
                .text()
                .append(
                    Component
                        .text("0", NamedTextColor.BLACK)
                        .clickEvent(ClickEvent.copyToClipboard("&0"))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to copy to clipboard!"))),
                ).append(
                    Component
                        .text("1", NamedTextColor.DARK_BLUE)
                        .clickEvent(ClickEvent.copyToClipboard("&1"))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to copy to clipboard!"))),
                ).append(
                    Component
                        .text("2", NamedTextColor.DARK_GREEN)
                        .clickEvent(ClickEvent.copyToClipboard("&2"))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to copy to clipboard!"))),
                ).append(
                    Component
                        .text("3", NamedTextColor.DARK_AQUA)
                        .clickEvent(ClickEvent.copyToClipboard("&3"))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to copy to clipboard!"))),
                ).append(
                    Component
                        .text("4", NamedTextColor.DARK_RED)
                        .clickEvent(ClickEvent.copyToClipboard("&4"))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to copy to clipboard!"))),
                ).append(
                    Component
                        .text("5", NamedTextColor.DARK_PURPLE)
                        .clickEvent(ClickEvent.copyToClipboard("&5"))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to copy to clipboard!"))),
                ).append(
                    Component
                        .text("6", NamedTextColor.GOLD)
                        .clickEvent(ClickEvent.copyToClipboard("&6"))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to copy to clipboard!"))),
                ).append(
                    Component
                        .text("7", NamedTextColor.GRAY)
                        .clickEvent(ClickEvent.copyToClipboard("&7"))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to copy to clipboard!"))),
                ).append(
                    Component
                        .text("8", NamedTextColor.DARK_GREEN)
                        .clickEvent(ClickEvent.copyToClipboard("&8"))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to copy to clipboard!"))),
                ).append(
                    Component
                        .text("9", NamedTextColor.BLUE)
                        .clickEvent(ClickEvent.copyToClipboard("&9"))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to copy to clipboard!"))),
                ).append(
                    Component
                        .text("a", NamedTextColor.GREEN)
                        .clickEvent(ClickEvent.copyToClipboard("&a"))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to copy to clipboard!"))),
                ).append(
                    Component
                        .text("b", NamedTextColor.AQUA)
                        .clickEvent(ClickEvent.copyToClipboard("&b"))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to copy to clipboard!"))),
                ).append(
                    Component
                        .text("c", NamedTextColor.RED)
                        .clickEvent(ClickEvent.copyToClipboard("&c"))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to copy to clipboard!"))),
                ).append(
                    Component
                        .text("d", NamedTextColor.LIGHT_PURPLE)
                        .clickEvent(ClickEvent.copyToClipboard("&d"))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to copy to clipboard!"))),
                ).append(
                    Component
                        .text("e", NamedTextColor.YELLOW)
                        .clickEvent(ClickEvent.copyToClipboard("&e"))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to copy to clipboard!"))),
                ).append(
                    Component
                        .text("f", NamedTextColor.WHITE)
                        .clickEvent(ClickEvent.copyToClipboard("&f"))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to copy to clipboard!"))),
                ).append(
                    Component
                        .text("k", NamedTextColor.WHITE, TextDecoration.OBFUSCATED)
                        .clickEvent(ClickEvent.copyToClipboard("&k"))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to copy to clipboard!"))),
                ).append(
                    Component
                        .text("l", NamedTextColor.WHITE, TextDecoration.BOLD)
                        .clickEvent(ClickEvent.copyToClipboard("&l"))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to copy to clipboard!"))),
                ).append(
                    Component
                        .text("m", NamedTextColor.WHITE, TextDecoration.STRIKETHROUGH)
                        .clickEvent(ClickEvent.copyToClipboard("&m"))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to copy to clipboard!"))),
                ).append(
                    Component
                        .text("n", NamedTextColor.WHITE, TextDecoration.UNDERLINED)
                        .clickEvent(ClickEvent.copyToClipboard("&n"))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to copy to clipboard!"))),
                ).append(
                    Component
                        .text("o", NamedTextColor.WHITE, TextDecoration.ITALIC)
                        .clickEvent(ClickEvent.copyToClipboard("&o"))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to copy to clipboard!"))),
                ).append(
                    Component
                        .text("r", NamedTextColor.WHITE)
                        .clickEvent(ClickEvent.copyToClipboard("&r"))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to copy to clipboard!"))),
                ).build()
    }

    override val commandName = "colours"
    override val description = "Returns a list of all of the colour codes"
    override val aliases = arrayListOf("colors")

    override val message = msg
}
