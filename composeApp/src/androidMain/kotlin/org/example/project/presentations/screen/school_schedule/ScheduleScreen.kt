package org.example.project.presentations.screen.school_schedule

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.R
import org.example.project.presentations.components.AppTopBar
import org.example.project.presentations.screen.school_schedule.components.ClassDetailBottomSheet
import org.example.project.presentations.screen.school_schedule.components.DateList
import org.example.project.presentations.screen.school_schedule.components.TodayScheduleList
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun ScheduleScreen(
    viewModel: ScheduleViewModel,
    onOpenNotificationScreen: () -> Unit = {},
    onOpenTimetable: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = LocalExtendedColors.current.background,
        topBar = {
            AppTopBar(
                iconRes = R.drawable.icon_school_schedule,
                title = "Lịch Học",
                onOpenNotificationScreen = onOpenNotificationScreen
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            DateList(
                modifier = Modifier.padding(top = 5.dp),
                selectedDayOfWeek = uiState.selectedDayOfWeek,
                onChangeDayOfWeek = { day ->
                    viewModel.onChangeDayOfWeek(day)
                }
            )

            TodayScheduleList(
                modifier = Modifier.padding(top = 20.dp),
                onOpenTimetable = onOpenTimetable,
                onOpenDetailCourseClass = {courseClass ->
                    viewModel.onOpenDetailCourseClass(courseClass)
                }
            )
        }
        
        if (uiState.showDetailCourseClass){
            ClassDetailBottomSheet(
                onDismiss = viewModel::onDismissDetailCourseClass,
                courseClass = uiState.selectedCourseClass!!,
                onViewMaterials = {
                    // TODO:  
                }
            )
        }
    }
}