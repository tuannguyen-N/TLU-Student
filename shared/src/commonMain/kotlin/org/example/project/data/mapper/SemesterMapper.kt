package org.example.project.data.mapper

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.plus
import org.example.project.data.remote.dto.semester.Semester

fun List<Semester>.toSemesterStringList(): List<String> = map { it.semesterName }

fun Semester.toWeekDateList(): List<String> {
    val formatter = LocalDate.Format {
        day()
        char('/')
        monthNumber()
        char('/')
        year()
    }

    val start = LocalDate.parse(startDate, formatter)
    val end = LocalDate.parse(endDate, formatter)

    val weeks = mutableListOf<String>()
    var current = start

    while (current <= end) {
        val weekEnd = minOf(current.plus(6, DateTimeUnit.DAY), end)
        weeks.add("${current.format(formatter)} - ${weekEnd.format(formatter)}")
        current = current.plus(7, DateTimeUnit.DAY)
    }

    return weeks
}

fun Semester.toStartWeekDate(): String {
    val formatter = LocalDate.Format {
        dayOfMonth()
        char('/')
        monthNumber()
        char('/')
        year()
    }

    val start = LocalDate.parse(startDate, formatter)
    val weekEnd = start.plus(6, DateTimeUnit.DAY)

    return "${start.format(formatter)} - ${weekEnd.format(formatter)}"
}