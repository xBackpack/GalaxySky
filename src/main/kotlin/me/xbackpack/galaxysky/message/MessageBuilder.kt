package me.xbackpack.galaxysky.message

import me.xbackpack.galaxysky.common.Builder
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration

@MessageDsl
class MessageBuilder :
    AbstractStylable(),
    Builder<Message> {
    private var children = mutableListOf<Component>()

    override var colour: TextColor? = null
    override val decorations = mutableMapOf<TextDecoration, Boolean>()

    fun section(block: MessageBuilder.() -> Unit) {
        val subGroup = MessageBuilder().apply(block)
        children += subGroup.build().component
    }

    fun text(
        content: String,
        block: TextStyleBuilder.() -> Unit = {},
    ) {
        val style = TextStyleBuilder().apply(block)
        val comp = Component.text(content).applyStyle(style)

        children += comp
    }

    fun addAll(messages: List<Message>) {
        children.addAll(messages.map(Message::component))
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

    fun newline() {
        children += Component.newline()
    }

    fun space() {
        children += Component.space()
    }

    override fun build(): Message {
        var result = Component.empty().append(children)

        result = result.applyStyle(this)

        return Message(result)
    }
}
