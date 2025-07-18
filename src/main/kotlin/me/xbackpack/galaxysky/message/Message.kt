package me.xbackpack.galaxysky.message

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer

@MessageDsl
data class Message(
    val component: Component,
) {
    fun toJson() = JSONComponentSerializer.json().serialize(component)

    companion object {
        fun create(builder: MessageBuilder.() -> Unit) = MessageBuilder().apply(builder).build()

        fun fromJson(json: String) = Message(JSONComponentSerializer.json().deserialize(json))
    }
}
