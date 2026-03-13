package org.example.project.data.remote.dto.exam_schedule

import com.kizitonwose.calendar.core.now
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

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
    val examStatus: String,
    val examType: String,
    val startTime: String,
    val subjectCode: String,
    val subjectName: String
) {
    val localExamDate: LocalDate
        get() = LocalDate.parse(examDate)
    val isPast: Boolean
        get() = localExamDate < LocalDate.now()
    val isToday: Boolean
        get() = localExamDate == LocalDate.now()
    val isFuture: Boolean
        get() = localExamDate > LocalDate.now()
}