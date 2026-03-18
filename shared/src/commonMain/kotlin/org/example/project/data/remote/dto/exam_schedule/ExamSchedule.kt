package org.example.project.data.remote.dto.exam_schedule

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlinx.serialization.Serializable
import kotlin.time.Clock

@Serializable
data class ExamSchedule(
    val attendanceStatus: String,
    val classCode: String,
    val endTime: String,
    val examAttempt: Int,
    val examDate: String,
    val examFormat: String,
    val examLocation: String,
    val examRoom: String,
    val examType: String,
    val startTime: String,
    val subjectCode: String,
    val subjectName: String
) {
    val localExamDate: LocalDate
        get() = LocalDate.parse(examDate)

    val today: LocalDate
        get() = Clock.System.todayIn(TimeZone.currentSystemDefault())

    val isPast: Boolean
        get() = localExamDate < today

    val isToday: Boolean
        get() = localExamDate == today

    val isFuture: Boolean
        get() = localExamDate > today
}