package me.xbackpack.galaxysky.service

import me.xbackpack.galaxysky.message.Stylable
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer

object FormattingService {
    fun legacyFormat(component: Component) = LegacyComponentSerializer.legacySection().serialize(component)

    fun applyStyle(
        component: Component,
        style: Stylable,
    ): Component {
        var result = component.color(style.internalColours)

        style.internalDecorations.forEach { decoration ->
            result = result.decoration(decoration, TextDecoration.State.TRUE)
        }

        return result
    }
}
