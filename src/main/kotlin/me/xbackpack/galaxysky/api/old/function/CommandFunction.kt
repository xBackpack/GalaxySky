package me.xbackpack.galaxysky.api.old.function

import me.xbackpack.galaxysky.api.old.common.CommandDsl

@CommandDsl
interface CommandFunction {
    fun runAll() {
        functions.forEach(Function0<Unit>::invoke)
    }

    val functions: MutableList<() -> Unit>
}
