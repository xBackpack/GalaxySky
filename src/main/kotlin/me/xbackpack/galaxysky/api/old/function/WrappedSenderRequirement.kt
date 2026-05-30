package me.xbackpack.galaxysky.api.old.function

import me.xbackpack.galaxysky.api.old.common.CommandDsl
import me.xbackpack.galaxysky.enum.command.SenderRequirement

@CommandDsl
data class WrappedSenderRequirement(
    val type: SenderRequirement,
    val permission: String? = null,
)
