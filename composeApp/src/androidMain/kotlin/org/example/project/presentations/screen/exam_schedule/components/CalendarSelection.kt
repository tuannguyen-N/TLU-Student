package org.example.project.presentations.screen.exam_schedule.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.core.DayPosition
import kotlinx.datetime.LocalDate
import kotlinx.datetime.YearMonth

@Composable
fun CalendarSection(
    calendarState: com.kizitonwose.calendar.compose.CalendarState,
    visibleMonth: YearMonth,
    selectedDate: LocalDate,
    today: LocalDate,
    examDateSet: Set<LocalDate>,
    onDateSelected: (LocalDate) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        MonthHeader(
            yearMonth = visibleMonth,
            calendarState = calendarState
        )

        Spacer(Modifier.height(8.dp))

        DayOfWeekRow()

        Spacer(Modifier.height(4.dp))

        HorizontalCalendar(
            state = calendarState,
            dayContent = { day ->
                CalendarDayCell(
                    day = day,
                    isSelected = day.date == selectedDate,
                    isToday = day.date == today,
                    hasExam = day.date in examDateSet,
                    onClick = { if (day.position == DayPosition.MonthDate) onDateSelected(day.date) }
                )
            },
            monthHeader = {}
        )
    }
}