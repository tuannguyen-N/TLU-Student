package org.example.project.presentations.screen.home

import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.project.data.local.entity.QuoteEntity
import org.example.project.data.remote.dto.me.StudentData
import org.example.project.data.remote.dto.semester.Semester
import org.example.project.domain.model.AlertUiModel
import org.example.project.domain.model.DaySchedule
import org.example.project.domain.model.EventAndNewUiModel
import org.example.project.domain.model.FeatureUiModel
import kotlin.time.Clock

data class HomeState(
    val studentInfo: StudentData? = null,
    val quickAccessList: List<FeatureUiModel> = emptyList(),
    val alerts: List<AlertUiModel> = emptyList(),
    val dayScheduleList: List<DaySchedule> = emptyList(),
    val examDayScheduleList: List<DaySchedule> = emptyList(),
    val newsAndEvents: List<EventAndNewUiModel> = emptyList(),
    val dailyQuote: QuoteEntity? = null,

    val loadingStudentInfo: Boolean = false,
    val loadingAlertList: Boolean = false,
    val loadingScheduleClassList: Boolean = false,
    val loadingEventList: Boolean = false,
    val isRefreshing: Boolean = false,
    val isAllNotificationsRead: Boolean = false
) {
    val upcomingSchedules: List<DaySchedule>
        get() {
            val now = Clock.System.now()
                .toLocalDateTime(TimeZone.currentSystemDefault()).time

            return (dayScheduleList + examDayScheduleList)
                .filter { schedule ->
                    val end = LocalTime.parse(schedule.endTime)
                    val endMinutes = end.hour * 60 + end.minute
                    val nowMinutes = now.hour * 60 + now.minute
                    endMinutes >= nowMinutes
                }
                .sortedBy { it.startTime }
                .take(3)
        }
}