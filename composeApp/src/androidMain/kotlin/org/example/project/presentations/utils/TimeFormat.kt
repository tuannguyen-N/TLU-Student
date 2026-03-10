package org.example.project.presentations.utils

import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.toLocalDateTime
import org.example.project.data.remote.dto.day_schedule.CourseClass
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.time.Clock

fun Long.toFormatTime(): String {
    val formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault())

    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .format(formatter)
}

fun getTodayDayOfWeek(): Int {
    val today = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .dayOfWeek  // MONDAY, TUESDAY,...

    return today.isoDayNumber
}

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