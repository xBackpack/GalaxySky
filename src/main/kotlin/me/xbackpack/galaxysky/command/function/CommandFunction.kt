package me.xbackpack.galaxysky.command.function

interface CommandFunction {
    fun runAll() {
        functions.forEach(Function0<Unit>::invoke)
    }

    val functions: MutableList<() -> Unit>
}
