package me.xbackpack.galaxysky.message

import net.kyori.adventure.text.Component

@MessageDsl
class MessageBuilder : Customisable {
    override val children = mutableListOf<Component>()

    fun build(): Message {
        val result = Component.empty().append(children)

        return Message(result)
    }
}
