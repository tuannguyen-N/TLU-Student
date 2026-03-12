package org.example.project.domain.model

import kotlinx.datetime.*
import kotlin.time.Clock

data class ExamDay(
    val date: LocalDate,
    val exams: List<ExamItem>
){
    val isPast: Boolean
        get () = date < Clock.System.todayIn(TimeZone.currentSystemDefault())

    val isToday: Boolean
        get () = date == Clock.System.todayIn(TimeZone.currentSystemDefault())
}