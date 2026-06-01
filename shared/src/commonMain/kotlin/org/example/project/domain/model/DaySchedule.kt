package org.example.project.domain.model

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

data class DaySchedule(
    val nameSubject: String,
    val location: String,
    val startTime: String,
    val endTime: String,
    val type: ScheduleType,
    val lecturerName: String? = null,
    val examType: String? = null,
) {
    val isCurrent: Boolean
        get() {
            return try {

                val now = Clock.System.now()
                    .toLocalDateTime(TimeZone.currentSystemDefault())
                    .time

                val start = kotlinx.datetime.LocalTime.parse(startTime)
                val end = kotlinx.datetime.LocalTime.parse(endTime)

                now in start..end

            } catch (e: Exception) {
                false
            }
        }
}

enum class ScheduleType {
    EXAM,
    STUDY
}
