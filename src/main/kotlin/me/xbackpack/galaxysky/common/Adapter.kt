package me.xbackpack.galaxysky.common

import me.xbackpack.galaxysky.message.Message
import net.kyori.adventure.text.Component

object Adapter {
    fun adapt(component: Component): Message = Message(component)
}
