package me.xbackpack.galaxysky.api.command.function

import me.xbackpack.galaxysky.api.command.common.CommandDsl
import me.xbackpack.galaxysky.enum.command.SenderRequirement

@CommandDsl
data class WrappedSenderRequirement(
    val type: SenderRequirement,
    val permission: String? = null,
)
