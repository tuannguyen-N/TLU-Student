package org.example.project.presentations.screen.exam_schedule.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.outlined.RestartAlt
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.minusMonths
import com.kizitonwose.calendar.core.plusMonths
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.YearMonth
import kotlinx.datetime.number
import kotlinx.datetime.plus
import org.example.project.data.mapper.toLocalDateSafe
import org.example.project.data.remote.dto.exam_schedule.ExamSchedule
import org.example.project.data.remote.dto.semester.Semester
import org.example.project.domain.model.ExamDay
import org.example.project.presentations.components.TabRowView
import org.example.project.presentations.components.TopCenterScreenBar
import org.example.project.presentations.screen.exam_schedule.ExamScheduleState
import org.example.project.presentations.theme.LocalExtendedColors
import org.example.project.presentations.utils.today

@Composable
fun ExamScheduleContent(
    uiState: ExamScheduleState,
    onBack: () -> Unit,
    onTabSelected: (Int) -> Unit,
    onToggleDropdown: () -> Unit,
    onSemesterChanged: (Semester) -> Unit,
    onChangeDate: (LocalDate) -> Unit,
    onResetData: () -> Unit
) {
    val color = LocalExtendedColors.current
    val tabs = listOf(
        "Lịch" to Icons.Filled.CalendarMonth,
        "Danh sách" to Icons.AutoMirrored.Filled.List
    )

    val selectedDate = uiState.selectedDate
    val (startMonth, endMonth) = remember(uiState.semesters, uiState.currentSemester) {
        val startM = uiState.semesters.minOfOrNull { it.startDate.toLocalDateSafe() }
            ?.let { YearMonth(it.year, it.month.number) }
            ?: YearMonth(today.year, today.month.number).minusMonths(12)

        val endM = uiState.currentSemester?.endDate?.toLocalDateSafe()?.let {
            YearMonth(it.year, it.month.number)
        } ?: YearMonth(today.year, today.month.number).plusMonths(12)

        val safeStart = minOf(startM, endM)
        val safeEnd = maxOf(startM, endM)
        safeStart to safeEnd
    }

    val firstVisibleMonth = remember(uiState.selectedSemester, startMonth, endMonth) {
        val selectedYM = uiState.selectedSemester?.let { semester ->
            val start = semester.startDate.toLocalDateSafe()
            val end = semester.endDate.toLocalDateSafe()
            val startYM = YearMonth(start.year, start.month.number)
            val endYM = YearMonth(end.year, end.month.number)

            YearMonth(today.year, today.month.number).coerceIn(startYM, endYM)
        } ?: YearMonth(today.year, today.month.number)

        selectedYM.coerceIn(startMonth, endMonth)
    }

    val calendarState = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = firstVisibleMonth,
        firstDayOfWeek = DayOfWeek.MONDAY
    )

    var visibleMonth by remember {
        mutableStateOf(calendarState.firstVisibleMonth.yearMonth)
    }

    LaunchedEffect(calendarState) {
        snapshotFlow { calendarState.isScrollInProgress }
            .collect { isScrolling ->
                if (!isScrolling) {
                    visibleMonth = calendarState.firstVisibleMonth.yearMonth
                }
            }
    }

    LaunchedEffect(uiState.resetTrigger) {
        val todayYM = YearMonth(today.year, today.month.number)
        calendarState.animateScrollToMonth(todayYM)
    }

    val examDateSet =
        remember(uiState.examDays) { uiState.examDays.map { it.localExamDay }.toSet() }
    val examDayMap = remember(uiState.examDays) {
        uiState.examDays.associateBy { it.localExamDay }
    }

    val selectedExam = examDayMap[selectedDate]
    val exams = selectedExam?.exams ?: emptyList()

    Scaffold(
        containerColor = color.background,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            TopCenterScreenBar(
                onBack = onBack,
                title = "Lịch thi",
                enableActionButton = true,
                onClickAction = onResetData,
                backgroundColor = Color.Transparent,
                contentColor = Color.Black,
                icon = Icons.Outlined.RestartAlt
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            TabRowView(
                tabs = tabs,
                selectedTab = uiState.selectedTab,
                onTabSelected = { tab -> onTabSelected(tab) }
            )

            Spacer(Modifier.height(12.dp))

            SemesterSelector(
                semesters = uiState.semesters,
                selectedSemester = uiState.selectedSemester,
                onSemesterSelected = {
                    onSemesterChanged(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onToggleDropdown = onToggleDropdown,
                isDropdownExpanded = uiState.isDropdownExpanded,
            )
            Spacer(Modifier.height(16.dp))

            if (uiState.selectedTab == 0) {
                CalendarSection(
                    calendarState = calendarState,
                    visibleMonth = visibleMonth,
                    selectedDate = selectedDate,
                    today = today,
                    examDateSet = examDateSet,
                    onDateSelected = { date -> onChangeDate(date) }
                )

                HorizontalDivider(color = color.lightGray)

                SelectedDaySummary(
                    date = selectedDate,
                    examInfo = selectedExam
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                ) {
                    if (exams.isNotEmpty()) {
                        items(exams) { exam ->
                            ExamCard(
                                exam = exam,
                                isPast = selectedExam!!.isPast,
                                isToday = selectedExam.isToday
                            )
                            Spacer(Modifier.height(8.dp))
                        }
                    } else {
                        item {
                            Text(
                                text = "Không có lịch thi",
                                style = MaterialTheme.typography.bodyMedium,
                                color = LocalExtendedColors.current.gray,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }
                }

            } else {

                ExamListView(
                    uiState = uiState,
                )
            }
        }
    }
}