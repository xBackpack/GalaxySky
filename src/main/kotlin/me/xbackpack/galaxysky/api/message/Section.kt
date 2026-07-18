package me.xbackpack.galaxysky.api.message

import me.xbackpack.galaxysky.service.FormattingService.applyStyle
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration

@MessageDsl
class Section :
    Customisable,
    Stylable {
    override val children = mutableListOf<Component>()

    override var internalColours: TextColor? = null
    override val internalDecorations = mutableSetOf<TextDecoration>()

    fun build() = Component.empty().append(children).applyStyle(this)
}
