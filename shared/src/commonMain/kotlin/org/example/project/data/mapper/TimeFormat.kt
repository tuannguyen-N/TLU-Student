package org.example.project.data.mapper

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.example.project.data.remote.dto.week_schedule.CourseClass
import kotlin.time.Clock

val today: LocalDate
    get() = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).date

fun Long.toFormatTime(): String {
    val time = kotlinx.datetime.Instant
        .fromEpochMilliseconds(this)
        .toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())

    val hour12 = (time.hour % 12).let { if (it == 0) 12 else it }
    val amPm = if (time.hour < 12) "AM" else "PM"

    return "${hour12.twoDigits()}:${time.minute.twoDigits()} $amPm"
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
    return "${time.hour.twoDigits()}:${time.minute.twoDigits()}"
}

fun String.toHourMinuteAmPm(): String {
    val time = LocalTime.parse(this)
    val hour12 = when {
        time.hour == 0 -> 12
        time.hour > 12 -> time.hour - 12
        else -> time.hour
    }
    val amPm = if (time.hour < 12) "AM" else "PM"

    return "${hour12.twoDigits()}:${time.minute.twoDigits()} $amPm"
}

fun CourseClass.isGoing(
    currentTime: LocalTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .time
): Boolean {
    val start = LocalTime.parse(startTime)
    val end = LocalTime.parse(endTime)

    return currentTime in start..end
}

fun CourseClass.getStatusText(
    currentTime: LocalTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .time
): String {
    val start = LocalTime.parse(startTime)
    val end = LocalTime.parse(endTime)

    val startMinutes = start.hour * 60 + start.minute
    val endMinutes = end.hour * 60 + end.minute
    val currentMinutes = currentTime.hour * 60 + currentTime.minute

    return when {
        currentMinutes > endMinutes -> {
            "Đã kết thúc"
        }

        currentMinutes in startMinutes..endMinutes -> {
            "Đang diễn ra"
        }

        startMinutes - currentMinutes <= 60 -> {
            val diff = startMinutes - currentMinutes
            "Sau $diff phút"
        }

        else -> {
            "Sắp diễn ra"
        }
    }
}

fun String.toSlashDate(): String {
    return this.replace("-", "/")
}

fun Int.twoDigits(): String = this.toString().padStart(2, '0')

//date
fun String.toDisplayDate(): String {
    val date = LocalDate.parse(this)
    return "${date.day.twoDigits()}/${date.month.number.twoDigits()}/${date.year}"
} // "21/01/2022"

fun String.toCreatedTime(): String {
    val dateTimePart = this.substringBefore(".")  // "2026-04-02T16:34:06"
    val time = LocalTime.parse(dateTimePart.substringAfter("T"))  // "16:34:06"
    val hour12 = when {
        time.hour == 0 -> 12
        time.hour > 12 -> time.hour - 12
        else -> time.hour
    }
    val amPm = if (time.hour < 12) "AM" else "PM"
    return "${hour12.twoDigits()}:${time.minute.twoDigits()} $amPm"
}  // "04:34 PM"

fun String.toCreatedDate(): String {
    val datePart = this.substringBefore("T")  // "2026-04-02"
    val date = LocalDate.parse(datePart)
    return "${date.day.twoDigits()}/${date.month.number.twoDigits()}/${date.year}"
}  // "02/04/2026"

fun String.toCreatedAgo(): String {
    val dateTimePart = this.substringBefore(".")         // "2026-04-02T16:34:06"
    val datePart = dateTimePart.substringBefore("T")     // "2026-04-02"
    val timePart = dateTimePart.substringAfter("T")      // "16:34:06"

    val createdDate = LocalDate.parse(datePart)
    val createdTime = LocalTime.parse(timePart)

    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val nowDate = now.date
    val nowTime = now.time

    val createdTotalMinutes = createdDate.toEpochDays() * 24 * 60 +
            createdTime.hour * 60 + createdTime.minute
    val nowTotalMinutes = nowDate.toEpochDays() * 24 * 60 +
            nowTime.hour * 60 + nowTime.minute

    val diffMinutes = nowTotalMinutes - createdTotalMinutes

    return when {
        diffMinutes < 1 -> "Vừa xong"
        diffMinutes < 60 -> "${diffMinutes} phút trước"
        diffMinutes < 1440 -> "${diffMinutes / 60} giờ trước"       // < 24 giờ
        diffMinutes < 43200 -> "${diffMinutes / 1440} ngày trước"   // < 30 ngày
        else -> "${diffMinutes / 43200} tháng trước"
    }
}

fun String.toFullDisplayDate(): String {
    val datePart = this.substringBefore("T") // "2026-04-24"
    val date = LocalDate.parse(datePart)

    return "${date.day.twoDigits()}/${date.month.number.twoDigits()}/${date.year}"
}

fun computeEnrollmentStatusText(
    startTime: String?,
    endTime: String?,
    hasSubjects: Boolean
): String? {
    if (startTime == null || endTime == null) return null

    return try {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val start = kotlinx.datetime.LocalDateTime.parse(startTime)
        val end = kotlinx.datetime.LocalDateTime.parse(endTime)

        fun diffLabel(
            from: kotlinx.datetime.LocalDateTime,
            to: kotlinx.datetime.LocalDateTime
        ): String {
            val fromEpoch = from.toInstant(TimeZone.currentSystemDefault()).epochSeconds
            val toEpoch = to.toInstant(TimeZone.currentSystemDefault()).epochSeconds
            val diffSeconds = toEpoch - fromEpoch
            val diffMs = diffSeconds / 60

            return when {
                diffMs >= 24 * 60 -> "${diffMs / (24 * 60)} ngày"
                diffMs >= 60 -> "${diffMs / 60} giờ"
                else -> "$diffMs phút"
            }
        }

        when {
            now < start -> "Đăng ký học sẽ diễn ra sau ${diffLabel(now, start)}"
            !hasSubjects && now >= start && now < end ->
                "Đăng ký học sẽ đóng sau ${diffLabel(now, end)}"

            else -> null
        }
    } catch (e: Exception) {
        null
    }
}