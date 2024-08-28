package me.xbackpack.galaxysky.commands.player

import me.xbackpack.galaxysky.util.commandTypes.MessageCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration

class Colours : MessageCommand {
    override val commandName = "colours"
    override val description = "Returns a list of all of the colour codes"
    override val aliases = arrayListOf("colors")
    override val requiresPlayer = false
    override val message =
        Component
            .text()
            .append(Component.text("0", NamedTextColor.BLACK))
            .append(Component.text("1", NamedTextColor.DARK_BLUE))
            .append(Component.text("2", NamedTextColor.DARK_GREEN))
            .append(Component.text("3", NamedTextColor.DARK_AQUA))
            .append(Component.text("4", NamedTextColor.DARK_RED))
            .append(Component.text("5", NamedTextColor.DARK_PURPLE))
            .append(Component.text("6", NamedTextColor.GOLD))
            .append(Component.text("7", NamedTextColor.GRAY))
            .append(Component.text("8", NamedTextColor.DARK_GREEN))
            .append(Component.text("9", NamedTextColor.BLUE))
            .append(Component.text("a", NamedTextColor.GREEN))
            .append(Component.text("b", NamedTextColor.AQUA))
            .append(Component.text("c", NamedTextColor.RED))
            .append(Component.text("d", NamedTextColor.LIGHT_PURPLE))
            .append(Component.text("e", NamedTextColor.YELLOW))
            .append(Component.text("f", NamedTextColor.WHITE))
            .append(Component.text("k", NamedTextColor.WHITE, TextDecoration.OBFUSCATED))
            .append(Component.text("l", NamedTextColor.WHITE, TextDecoration.BOLD))
            .append(Component.text("m", NamedTextColor.WHITE, TextDecoration.STRIKETHROUGH))
            .append(Component.text("n", NamedTextColor.WHITE, TextDecoration.UNDERLINED))
            .append(Component.text("o", NamedTextColor.WHITE, TextDecoration.ITALIC))
            .append(Component.text("r", NamedTextColor.WHITE))
            .build()
}
