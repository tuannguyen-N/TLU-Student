package org.example.project.data.mapper

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import org.example.project.data.remote.dto.exam_schedule.ExamSchedule
import org.example.project.data.remote.dto.week_schedule.WeeklyScheduleData
import org.example.project.domain.model.ExamDay

fun List<ExamSchedule>.toExamDays(): List<ExamDay> {
    return this
        .groupBy { LocalDate.parse(it.examDate) }
        .map { (date, exams) -> ExamDay(date, exams) }
        .sortedBy { it.localExamDay }
}

fun WeeklyScheduleData.toDisplayWeekDate(): String {
    val apiFormatter = LocalDate.Format {
        year(); char('-'); monthNumber(); char('-'); dayOfMonth()
    }
    val displayFormatter = LocalDate.Format {
        dayOfMonth(); char('/'); monthNumber(); char('/'); year()
    }
    val start = LocalDate.parse(startDate, apiFormatter)
    val end = LocalDate.parse(endDate, apiFormatter)
    return "${start.format(displayFormatter)} - ${end.format(displayFormatter)}"
}