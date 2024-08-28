package me.xbackpack.galaxysky

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.ln
import kotlin.math.pow

/** Converts a string to title case*/
fun String.snakeToTitle(): String =
    this
        .split("_")
        .dropLastWhile { it.isEmpty() }
        .asSequence()
        .map { it[0].uppercaseChar().toString() + it.substring(1).lowercase() }
        .joinToString(" ")

/** Converts a number into a more compact form e.g. 1350 to 1.35k or 3925820 to 3.926m */
fun Int.compact(): String {
    if (this < 1000) return this.toString()
    val suffixes = listOf("", "k", "m")
    val value = this.toDouble()

    val exp = (ln(value) / ln(1000.0)).toInt()
    val significantValue = value / 1000.0.pow(exp.toDouble())

    val formattedValue =
        when {
            significantValue >= 100 -> String.format("%.0f", significantValue)
            significantValue >= 10 -> String.format("%.1f", significantValue)
            else -> String.format("%.2f", significantValue)
        }

    return "${formattedValue.trimEnd('0').trimEnd('.')}${suffixes[exp]}"
}

/** Formats an integer as a time (the integer must be in seconds) */
fun Int.formatTime(): String {
    val secondsInMinute = 60
    val secondsInHour = 3600 // secondsInMinute * 60
    val secondsInDay = 86400 // secondsInHour * 24
    val secondsInWeek = 604800 // secondsInDay * 7

    val weeks = this / secondsInWeek
    val days = (this % secondsInWeek) / secondsInDay
    val hours = (this % secondsInDay) / secondsInHour
    val minutes = (this % secondsInHour) / secondsInMinute
    val seconds = this % secondsInMinute

    return buildString {
        append("$weeks ${if (weeks == 1) "week" else "weeks"}, ")
        append("$days ${if (days == 1) "day" else "days"}, ")
        append("$hours ${if (hours == 1) "hour" else "hours"}, ")
        append("$minutes ${if (minutes == 1) "minute" else "minutes"} and ")
        append("$seconds ${if (seconds == 1) "second" else "seconds"}")
    }.trim()
}

fun Int.formatTimeSimple(): String {
    val secondsInMinute = 60
    val secondsInHour = 3600 // secondsInMinute * 60
    val secondsInDay = 86400 // secondsInHour * 24
    val secondsInWeek = 604800 // secondsInDay * 7

    val weeks = this / secondsInWeek
    val days = (this % secondsInWeek) / secondsInDay
    val hours = (this % secondsInDay) / secondsInHour
    val minutes = (this % secondsInHour) / secondsInMinute
    val seconds = this % secondsInMinute

    return when {
        weeks > 0 -> {
            if (days > 0) {
                "${weeks}w ${days}d"
            } else if (hours > 0) {
                "${weeks}w ${hours}h"
            } else if (minutes > 0) {
                "${weeks}w ${minutes}m"
            } else {
                "${weeks}w ${seconds}s"
            }
        }
        days > 0 -> {
            if (hours > 0) {
                "${days}d ${hours}h"
            } else if (minutes > 0) {
                "${days}d ${minutes}m"
            } else {
                "${days}d ${seconds}s"
            }
        }
        hours > 0 -> {
            if (minutes > 0) {
                "${hours}h ${minutes}m"
            } else {
                "${hours}h ${seconds}s"
            }
        }
        minutes > 0 -> "${minutes}m ${seconds}s"
        else -> "${seconds}s"
    }
}

/** Converts a long to a date (the long must be in milliseconds) */
fun Long.asDate(): String =
    LocalDateTime
        .ofInstant(
            Instant.ofEpochMilli(this),
            ZoneId.systemDefault(),
        ).format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' HH:mm:ss"))
