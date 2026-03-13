package org.example.project.data.mapper

import kotlinx.datetime.LocalDate
import org.example.project.data.remote.dto.exam_schedule.ExamSchedule
import org.example.project.domain.model.ExamDay

fun List<ExamSchedule>.toExamDays(): List<ExamDay> {
    return this
        .groupBy { LocalDate.parse(it.examDate) }
        .map { (date, exams) -> ExamDay(date, exams) }
        .sortedBy { it.localExamDay }
}