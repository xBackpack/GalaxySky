package me.xbackpack.galaxysky.util

import net.kyori.adventure.text.Component

data class PluginPermission(
    val permission: String,
    val message: Component = Component.empty(),
)
