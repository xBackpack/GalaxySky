package me.xbackpack.galaxysky.message

import me.xbackpack.galaxysky.service.FormattingService
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration

class SectionBuilder :
    Customisable,
    Stylable {
    override val children = mutableListOf<Component>()

    override var internalColours: TextColor? = null
    override val internalDecorations = mutableSetOf<TextDecoration>()

    fun build(): Message {
        val result = FormattingService.applyStyle(Component.empty().append(children), this)

        return Message(result)
    }
}
