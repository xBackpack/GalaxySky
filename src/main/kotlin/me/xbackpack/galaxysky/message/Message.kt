package me.xbackpack.galaxysky.message

import net.kyori.adventure.text.Component

@MessageDSL
data class Message(
    val component: Component,
)
