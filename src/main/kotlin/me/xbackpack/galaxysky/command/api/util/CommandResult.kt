package me.xbackpack.galaxysky.command.api.util

data class CommandResult(
    private val result: Boolean,
) {
    fun wasSuccessful() = result
}
