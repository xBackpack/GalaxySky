package me.xbackpack.galaxysky.message

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration

interface Customisable {
    val children: MutableList<Component>

    fun section(block: SectionBuilder.() -> Unit) {
        val subGroup = SectionBuilder().apply(block)
        children += subGroup.build().root
    }

    fun text(
        content: String,
        block: TextStyleBuilder.() -> Unit = {},
    ) {
        val style = TextStyleBuilder().apply(block)
        val comp = Component.text(content).applyStyle(style)

        children += comp
    }

    fun link(content: String) {
        val comp =
            Component
                .text(content)
                .color(NamedTextColor.GREEN)
                .decorate(TextDecoration.UNDERLINED)
                .clickEvent(ClickEvent.openUrl(content))
                .hoverEvent(HoverEvent.showText(Component.text("Click to open!")))

        children += comp
    }

    fun snippet(content: String) {
        val comp =
            Component
                .text(content)
                .color(NamedTextColor.GREEN)
                .decorate(TextDecoration.UNDERLINED)
                .clickEvent(ClickEvent.copyToClipboard(content))
                .hoverEvent(HoverEvent.showText(Component.text("Click to copy to clipboard!")))

        children += comp
    }

    fun component(message: Message) {
        children += message.root
    }

    fun newline() {
        children += Component.newline()
    }

    fun space() {
        children += Component.space()
    }

    fun toLore(): List<Component> {
        val lore = mutableListOf<Component>()

        var currentLine = Component.empty()

        children.forEach { child ->
            currentLine =
                if (child != Component.newline()) {
                    currentLine.append(child)
                } else {
                    lore.add(currentLine)
                    Component.empty()
                }
        }

        lore.add(currentLine)

        return lore
    }
}
