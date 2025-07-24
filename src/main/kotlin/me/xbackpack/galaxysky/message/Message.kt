package me.xbackpack.galaxysky.message

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer

@MessageDsl
data class Message(
    val root: Component,
) {
    companion object {
        fun empty() = Message(Component.empty())

        fun create(builder: MessageBuilder.() -> Unit) = MessageBuilder().apply(builder).build()

        fun fromJson(json: String) = Message(JSONComponentSerializer.json().deserialize(json))
    }
}
