package org.example.project.presentations.screen.exam_schedule.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.plusMonths
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.YearMonth
import kotlinx.datetime.number
import org.example.project.data.remote.dto.semester.Semester
import org.example.project.domain.model.ExamDay
import org.example.project.domain.model.ExamScheduleState
import org.example.project.presentations.components.TabRowView
import org.example.project.presentations.components.TopCenterScreenBar
import org.example.project.presentations.theme.LocalExtendedColors
import org.example.project.presentations.utils.today

@Composable
fun ExamScheduleContent(
    uiState: ExamScheduleState,
    examDays: List<ExamDay>,
    onBack: () -> Unit,
    onTabSelected: (Int) -> Unit,
    onToggleDropdown: () -> Unit,
    onSemesterChanged: (Semester)-> Unit
) {
    val color = LocalExtendedColors.current
    val tabs = listOf(
        "Lịch" to Icons.Filled.CalendarMonth,
        "Danh sách" to Icons.AutoMirrored.Filled.List
    )

    var selectedDate by remember { mutableStateOf(today) }

    val startMonth = remember { YearMonth(today.year, today.month.number) }
    val endMonth = remember { startMonth.plusMonths(12) }
    val calendarState = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = startMonth,
        firstDayOfWeek = DayOfWeek.MONDAY
    )

    val visibleMonth by remember {
        derivedStateOf { calendarState.firstVisibleMonth.yearMonth }
    }

    val examDateSet = remember(examDays) { examDays.map { it.localExamDay }.toSet() }
    val selectedExam = remember(selectedDate, examDays) {
        examDays.find { it.localExamDay == selectedDate }
    }

    Scaffold(
        containerColor = color.background,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            TopCenterScreenBar(
                onBack = onBack,
                title = "Lịch thi",
                enableActionButton = false,
                onClickAction = {},
                backgroundColor = Color.Transparent,
                contentColor = Color.Black
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            TabRowView(
                tabs = tabs,
                selectedTab = uiState.selectedTab,
                onTabSelected = {onTabSelected(it) }
            )

            Spacer(Modifier.height(12.dp))

            if (uiState.selectedTab == 0) {
                CalendarSection(
                    calendarState = calendarState,
                    visibleMonth = visibleMonth,
                    selectedDate = selectedDate,
                    today = today,
                    examDateSet = examDateSet,
                    onDateSelected = { selectedDate = it }
                )

                HorizontalDivider(color = color.grayButton)

                SelectedDaySummary(
                    date = selectedDate,
                    examInfo = selectedExam
                )
            } else {
                ExamListView(
                    uiState = uiState,
                    onSemesterChanged = { onSemesterChanged(it) },
                    isDropdownExpanded = uiState.isDropdownExpanded,
                    onToggleDropdown = onToggleDropdown
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExamScheduleContentPreview() {

    val semester1 = Semester(
        semesterName = "HK1 2025-2026",
        startDate = "2025-09-01",
        endDate = "2026-01-15"
    )

    val semester2 = Semester(
        semesterName = "HK2 2025-2026",
        startDate = "2026-02-01",
        endDate = "2026-06-15"
    )

    val semester3 = Semester(
        semesterName = "HK1 2026-2027",
        startDate = "2026-09-01",
        endDate = "2027-01-15"
    )

    ExamScheduleContent(
        uiState = ExamScheduleState(
            semesters = listOf(
                semester1,
                semester2,
                semester3
            ),
            selectedSemester = semester3,
            currentSemester = semester3,
            selectedTab = 1,
            isDropdownExpanded = false,
            isLoading = false,
            error = null
        ),
        examDays = emptyList(),
        onToggleDropdown = {},
        onSemesterChanged = {},
        onTabSelected = {},
        onBack = {}
    )
}