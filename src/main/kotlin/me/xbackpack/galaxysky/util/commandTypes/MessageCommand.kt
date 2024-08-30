package me.xbackpack.galaxysky.util.commandTypes

import net.kyori.adventure.text.Component

interface MessageCommand : CommandBase {
    val message: Component
}
