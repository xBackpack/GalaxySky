package me.xbackpack.galaxysky.service

import me.xbackpack.galaxysky.message.Stylable
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import kotlin.math.log
import kotlin.math.min
import kotlin.math.pow

object FormattingService {
    const val WEEK_SECONDS = 604800
    const val DAY_SECONDS = 86400
    const val HOUR_SECONDS = 3600
    const val MINUTE_SECONDS = 60

    fun legacyFormat(component: Component) = LegacyComponentSerializer.legacySection().serialize(component)

    fun applyStyle(
        component: Component,
        style: Stylable,
    ): Component {
        var result = component.color(style.internalColours)

        style.internalDecorations.forEach { decoration ->
            result = result.decoration(decoration, TextDecoration.State.TRUE)
        }

        return result
    }

    fun shortenStat(int: Int): String {
        val argument = int.toDouble()
        val base = 1000.0
        val trueExp = log(argument, base).toInt()

        val exp = min(trueExp, 2)

        val suffixes = listOf("", "k", "m")

        val suffix = suffixes[exp]

        val finalValue = argument % base.pow(exp)

        val formattedValue =
            when {
                finalValue >= 100 -> String.format("%.0f", finalValue)
                finalValue >= 10 -> String.format("%.1f", finalValue)
                else -> String.format("%.2f", finalValue)
            }.trimEnd('0').trimEnd('.')

        return "$formattedValue$suffix"
    }

    fun shortenTime(secondsInput: Int): String {
        val parts = StringBuilder()
        var secondsRemaining = secondsInput

        val units =
            listOf(
                WEEK_SECONDS to "w",
                DAY_SECONDS to "d",
                HOUR_SECONDS to "h",
                MINUTE_SECONDS to "m",
                1 to "s",
            )

        var count = 0
        for ((unitSeconds, suffix) in units) {
            val value = secondsRemaining / unitSeconds
            if (value > 0) {
                if (parts.isNotEmpty()) parts.append(" ")
                parts.append("$value$suffix")
                secondsRemaining %= unitSeconds
                count++
                if (count == 2) break
            }
        }

        return parts.toString()
    }

    fun shortenTimeFull(secondsInput: Int): String {
        val parts = StringBuilder()
        var secondsRemaining = secondsInput

        val units =
            listOf(
                WEEK_SECONDS to "week",
                DAY_SECONDS to "day",
                HOUR_SECONDS to "hour",
                MINUTE_SECONDS to "minute",
                1 to "second",
            )

        for ((unitSeconds, label) in units) {
            val value = secondsRemaining / unitSeconds
            if (value > 0) {
                if (parts.isNotEmpty()) parts.append(", ")
                parts.append("$value $label${if (value > 1) "s" else ""}")
                secondsRemaining %= unitSeconds
            }
        }

        val result = parts.toString()
        return if (result.isEmpty()) "0 seconds" else result.replaceLast(", ", " and ")
    }

    private fun String.replaceLast(
        oldValue: String,
        newValue: String,
    ): String {
        val lastIndex = this.lastIndexOf(oldValue)
        return if (lastIndex != -1) this.substring(0, lastIndex) + newValue + this.substring(lastIndex + oldValue.length) else this
    }
}
