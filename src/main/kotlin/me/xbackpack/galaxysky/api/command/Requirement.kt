package me.xbackpack.galaxysky.api.command

import me.xbackpack.galaxysky.service.LuckPermsService.isStaff
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

enum class Requirement(
    predicate: (CommandSender) -> Boolean,
) {
    PLAYER({ it is Player }),
    STAFF({ it is Player && (it.isOp || it.isStaff()) }),
    CONSOLE({ it is ConsoleCommandSender }),
    STAFF_OR_CONSOLE({ it.isOp || (it is Player && it.isStaff()) }),
}
