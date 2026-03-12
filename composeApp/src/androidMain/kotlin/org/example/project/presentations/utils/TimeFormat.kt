package org.example.project.presentations.utils

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import org.example.project.data.remote.dto.week_schedule.CourseClass
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.time.Clock

val today: LocalDate
    get() = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).date

fun Long.toFormatTime(): String {
    val formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault())

    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .format(formatter)
}

fun getTodayDayOfWeek(): Int = today.dayOfWeek.isoDayNumber

private fun getWeekOf(date: LocalDate): Pair<String, String> {
    val dayOfWeek = date.dayOfWeek.isoDayNumber
    val start = date.minus(dayOfWeek - 1, DateTimeUnit.DAY)
    val end = date.plus(7 - dayOfWeek, DateTimeUnit.DAY)
    return Pair(start.toString(), end.toString())
}

fun getCurrentWeek() = getWeekOf(today)
fun getNextWeek(date: LocalDate) = getWeekOf(date.plus(7, DateTimeUnit.DAY))
fun getPreviousWeek(date: LocalDate) = getWeekOf(date.minus(7, DateTimeUnit.DAY))

fun String.toHourMinute(): String {
    val time = LocalTime.parse(this)
    return "%02d:%02d".format(time.hour, time.minute)
}

fun CourseClass.isGoing(): Boolean {
    val now = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .time
    val start = LocalTime.parse(startTime)
    val end = LocalTime.parse(endTime)

    return now in start..end
}