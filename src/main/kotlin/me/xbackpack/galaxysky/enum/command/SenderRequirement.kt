package me.xbackpack.galaxysky.enum.command

import me.xbackpack.galaxysky.api.command.function.WrappedSenderRequirement

enum class SenderRequirement {
    PLAYER,
    PERMISSION,
    STAFF,
    STAFF_OR_PERMISSION,
    STAFF_OR_CONSOLE,
    STAFF_OR_PERMISSION_OR_CONSOLE,
    CONSOLE,
    ;

    operator fun invoke(permission: String? = null) = WrappedSenderRequirement(this, permission)
}
