package org.example.project.presentations.screen.timetable.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.example.project.data.remote.dto.week_schedule.CourseClass
import org.example.project.data.remote.dto.week_schedule.DailySchedule

@Composable
fun TimetableScrollableArea(
    dailySchedules: List<DailySchedule>,
    onShowSubjectDetail: (CourseClass) -> Unit,
) {
    val verticalScroll = rememberScrollState()
    val horizontalScroll = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(horizontalScroll)
            .verticalScroll(verticalScroll)
    ) {
        TimetableGrid(
            dailySchedules = dailySchedules,
            onShowSubjectDetail = { onShowSubjectDetail(it) }
        )
    }
}