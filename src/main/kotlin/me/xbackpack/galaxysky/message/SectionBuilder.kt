package me.xbackpack.galaxysky.message

import me.xbackpack.galaxysky.common.Builder
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration

class SectionBuilder :
    Customisable,
    Stylable,
    Builder<Message> {
    override val children = mutableListOf<Component>()

    override var internalColour: TextColor? = null
    override val internalDecorations = mutableSetOf<TextDecoration>()

    override fun build(): Message {
        val result = Component.empty().append(children).applyStyle(this)

        return Message(result)
    }
}
