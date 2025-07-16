package me.xbackpack.galaxysky.message

import net.kyori.adventure.text.Component

@MessageDsl
data class Message(
    val component: Component,
) {
    companion object {
        fun create(builder: MessageBuilder.() -> Unit) = MessageBuilder().apply(builder).build()

        fun empty() = Message(Component.empty())
    }
}
