package me.xbackpack.galaxysky.message

import me.xbackpack.galaxysky.service.FormattingService
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer

interface Customisable {
    val children: MutableList<Component>

    fun section(block: SectionBuilder.() -> Unit) {
        val subGroup = SectionBuilder().apply(block)
        children += subGroup.build().root
    }

    fun text(
        content: String,
        builder: TextStyleBuilder.() -> Unit = {},
    ) {
        val style = TextStyleBuilder().apply(builder)
        val comp = FormattingService.applyStyle(Component.text(content), style)

        children += comp
    }

    fun textGradient(
        content: String,
        startHex: String,
        endHex: String,
        vararg decorations: TextDecoration,
    ) {
        val msg = "<gradient:$startHex:$endHex>$content</gradient>"

        val component = MiniMessage.miniMessage().deserialize(msg).decorations(decorations.toSet(), true)

        children += component
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

    fun component(component: Component) {
        children += component
    }

    fun componentFromLegacyString(string: String) {
        children += LegacyComponentSerializer.legacyAmpersand().deserialize(string)
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
