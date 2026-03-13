package org.example.project.presentations.screen.exam_schedule.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number
import org.example.project.data.remote.dto.exam_schedule.ExamSchedule
import org.example.project.data.remote.dto.semester.Semester
import org.example.project.domain.model.ExamDay
import org.example.project.domain.model.ExamScheduleState
import org.example.project.presentations.components.LabelView
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun ExamListView(
    uiState: ExamScheduleState,
    onSemesterChanged: (Semester) -> Unit,
    isDropdownExpanded: Boolean,
    onToggleDropdown: () -> Unit
) {
    val targetExamDayIndex = uiState.examDays.indexOfFirst { it.isToday }
        .takeIf { it != -1 } ?: uiState.examDays.indexOfFirst { !it.isPast }.takeIf { it != -1 }
    ?: 0
    val listState = rememberLazyListState()

    LaunchedEffect(uiState.examDays) {
        val todayIndex = calculateScrollIndex(uiState.examDays, targetExamDayIndex)
        listState.animateScrollToItem(todayIndex)
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        item {
            SemesterSelector(
                semesters = uiState.semesters,
                selectedSemester = uiState.selectedSemester,
                onSemesterSelected = {
                    onSemesterChanged(it)
                },
                modifier = Modifier.fillMaxWidth(),
                onToggleDropdown = onToggleDropdown,
                isDropdownExpanded = isDropdownExpanded
            )
            Spacer(Modifier.height(16.dp))
        }

        uiState.examDays.forEach { examDay ->
            item {
                ExamDayHeader(
                    date = examDay.localExamDay,
                    isPast = examDay.isPast,
                    isToday = examDay.isToday
                )
                Spacer(Modifier.height(8.dp))
            }

            items(examDay.exams) { exam ->
                ExamCard(exam = exam, isPast = examDay.isPast, isToday = examDay.isToday)
                Spacer(Modifier.height(8.dp))
            }
            item { Spacer(Modifier.height(8.dp)) }
        }
    }
}

private fun calculateScrollIndex(
    examDays: List<ExamDay>,
    targetIndex: Int
): Int {
    var index = 1
    for (i in 0 until targetIndex) {
        index += 1
        index += examDays[i].exams.size
        index += 1
    }
    return index
}

@Composable
private fun ExamDayHeader(
    date: LocalDate,
    isPast: Boolean,
    isToday: Boolean
) {
    val shortDayMap = mapOf(
        DayOfWeek.MONDAY to "Thứ 2",
        DayOfWeek.TUESDAY to "Thứ 3",
        DayOfWeek.WEDNESDAY to "Thứ 4",
        DayOfWeek.THURSDAY to "Thứ 5",
        DayOfWeek.FRIDAY to "Thứ 6",
        DayOfWeek.SATURDAY to "Thứ 7",
        DayOfWeek.SUNDAY to "Chủ Nhật"
    )
    val dayLabel = shortDayMap[date.dayOfWeek] ?: ""
    val dateText = "$dayLabel, ${date.day.toString().padStart(2, '0')}/${
        date.month.number.toString().padStart(2, '0')
    }/${date.year}"

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = dateText,
            style = MaterialTheme.typography.labelLarge.copy(
                color = LocalExtendedColors.current.gray,
                letterSpacing = 0.5.sp
            )
        )

        if (isPast) {
            LabelView(
                text = "Đã qua",
                backgroundColor = LocalExtendedColors.current.gray.copy(alpha = 0.1f),
                textColor = LocalExtendedColors.current.gray
            )
        } else if (isToday) {
            LabelView(
                text = "Hôm nay",
                backgroundColor = LocalExtendedColors.current.green.copy(alpha = 0.1f),
                textColor = LocalExtendedColors.current.green
            )
        } else {
            LabelView(
                text = "Sắp tới",
                backgroundColor = LocalExtendedColors.current.fontBlue.copy(alpha = 0.1f),
                textColor = LocalExtendedColors.current.fontBlue
            )
        }
    }
}

@Composable
private fun ExamCard(
    exam: ExamSchedule,
    isPast: Boolean,
    isToday: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        LocalExtendedColors.current.gray.copy(alpha = 0.1f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.LibraryBooks,
                    contentDescription = null,
                    tint = if (isPast) LocalExtendedColors.current.gray else if (isToday) LocalExtendedColors.current.green else Color.Black,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(Modifier.width(14.dp))

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = exam.subjectName,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = if (isPast) LocalExtendedColors.current.gray else Color.Black
                    )
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.AccessTime,
                            contentDescription = null,
                            tint = LocalExtendedColors.current.gray,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "${exam.startTime} - ${exam.endTime}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = LocalExtendedColors.current.gray
                            )
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = null,
                            tint = LocalExtendedColors.current.gray,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "Phòng: ${exam.examRoom}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = LocalExtendedColors.current.gray
                            )
                        )
                    }
                }
            }
        }
    }
}