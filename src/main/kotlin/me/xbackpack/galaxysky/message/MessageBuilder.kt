package me.xbackpack.galaxysky.message

import me.xbackpack.galaxysky.common.Builder
import net.kyori.adventure.text.Component

@MessageDsl
class MessageBuilder :
    Customisable,
    Builder<Message> {
    override val children = mutableListOf<Component>()

    override fun build(): Message {
        val result = Component.empty().append(children)

        return Message(result)
    }
}
