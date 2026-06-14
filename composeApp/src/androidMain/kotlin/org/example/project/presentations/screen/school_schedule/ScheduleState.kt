package org.example.project.presentations.screen.school_schedule

import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.project.data.remote.dto.week_schedule.CourseClass
import kotlin.time.Clock

data class ScheduleState(
    val courseClasses: List<CourseClass>? = emptyList(),
    val selectedCourseClass: CourseClass? = null,
    val showDetailCourseClass: Boolean = false,
    val showDetailLecturerInfo: Boolean = false,
    val currentDay: Int,
    val selectedDayOfWeek: Int,
    val currentTime: LocalTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).time,

    val isAllNotificationsRead: Boolean = false,
    val isLoading: Boolean = false
)