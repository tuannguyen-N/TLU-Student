package org.example.project.presentations.screen.timetable.components

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.launch
import org.example.project.data.remote.dto.week_schedule.CourseClass
import org.example.project.data.remote.dto.week_schedule.DailySchedule

@Composable
fun TimetableScrollableArea(
    dailySchedules: List<DailySchedule>,
    onShowSubjectDetail: (CourseClass) -> Unit,
) {
    val verticalScroll = rememberScrollState()
    val horizontalScroll = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    coroutineScope.launch {
                        horizontalScroll.scrollBy(-dragAmount.x)
                        verticalScroll.scrollBy(-dragAmount.y)
                    }
                }
            }
    ) {
        Box(
            modifier = Modifier
                .horizontalScroll(horizontalScroll, enabled = false)
                .verticalScroll(verticalScroll, enabled = false)
        ) {
            TimetableGrid(
                dailySchedules = dailySchedules,
                onShowSubjectDetail = onShowSubjectDetail
            )
        }
    }
}