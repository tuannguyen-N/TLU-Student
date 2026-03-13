package org.example.project.domain.model

import kotlinx.datetime.*
import org.example.project.data.remote.dto.exam_schedule.ExamSchedule
import kotlin.time.Clock

data class ExamDay(
    val localExamDay: LocalDate,
    val exams: List<ExamSchedule>
){
    val isPast: Boolean
        get () = localExamDay < Clock.System.todayIn(TimeZone.currentSystemDefault())

    val isToday: Boolean
        get () = localExamDay == Clock.System.todayIn(TimeZone.currentSystemDefault())
}