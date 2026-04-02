package org.example.project.data.mapper

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.YearMonth
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.plus
import org.example.project.data.remote.dto.semester.Semester

import org.example.project.data.local.entity.SemesterEntity

fun Semester.toEntity(): SemesterEntity {
    return SemesterEntity(
        id = id,
        semesterName = semesterName,
        semesterCode = semesterCode,
        academicYears = academicYears,
        semesterNumber = semesterNumber,
        startDate = startDate,
        endDate = endDate,
        isActive = isActive
    )
}

fun SemesterEntity.toSemester(): Semester {
    return Semester(
        id = id,
        semesterName = semesterName,
        semesterCode = semesterCode,
        academicYears = academicYears,
        semesterNumber = semesterNumber,
        startDate = startDate,
        endDate = endDate,
        isActive = isActive
    )
}

fun List<Semester>.toSemesterStringList(): List<String> = map { it.semesterName }

fun Semester.toWeekDateList(): List<String> {
    val apiFormatter = LocalDate.Format {
        year()
        char('-')
        monthNumber()
        char('-')
        dayOfMonth()
    }

    val start = LocalDate.parse(startDate, apiFormatter)
    val end = LocalDate.parse(endDate, apiFormatter)

    val weeks = mutableListOf<String>()
    var current = start

    while (current <= end) {
        val weekEnd = minOf(current.plus(6, DateTimeUnit.DAY), end)
        weeks.add("$current - $weekEnd") // "yyyy-MM-dd - yyyy-MM-dd"
        current = current.plus(7, DateTimeUnit.DAY)
    }

    return weeks
}

fun Semester.toStartWeekDate(): String {
    val start = startDate.toLocalDateSafe() // fallbacks to dd/MM/yyyy if not ISO
    val weekEnd = start.plus(6, DateTimeUnit.DAY)
    return "$start - $weekEnd" // "yyyy-MM-dd - yyyy-MM-dd"
}

fun String.toDisplayWeekDate(): String {
    val (start, end) = split(" - ")
    val displayFormatter = LocalDate.Format {
        day(); char('/'); monthNumber(); char('/'); year()
    }
    return "${LocalDate.parse(start).format(displayFormatter)} - ${
        LocalDate.parse(end).format(displayFormatter)
    }"
}

fun Semester.toYearMonth(): YearMonth {
    val date = startDate.toLocalDateSafe()
    return YearMonth(date.year, date.monthNumber)
}

fun String.toLocalDateSafe(): LocalDate {
    return if (contains("-")) {
        LocalDate.parse(this)
    } else {
        val formatter = LocalDate.Format {
            dayOfMonth(); char('/'); monthNumber(); char('/'); year()
        }
        LocalDate.parse(this, formatter) // dd/MM/yyyy
    }
}