package me.xbackpack.galaxysky.api.command.function

import me.xbackpack.galaxysky.api.command.common.CommandDsl

@CommandDsl
interface CommandFunction {
    fun runAll() {
        functions.forEach(Function0<Unit>::invoke)
    }

    val functions: MutableList<() -> Unit>
}
