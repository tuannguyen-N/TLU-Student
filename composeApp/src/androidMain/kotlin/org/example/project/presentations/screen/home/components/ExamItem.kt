package org.example.project.presentations.screen.home.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.example.project.data.remote.dto.exam_schedule.ExamSchedule
import org.example.project.presentations.theme.ExtendedColors


@Composable
fun ExamCurrent(modifier: Modifier = Modifier, color: ExtendedColors, item: ExamSchedule) {
    ScheduleItemLayout(
        modifier = modifier,
        color = color,
        startTime = item.startTime,
        endTime = item.endTime,
        isGoing = true
    ) {
        BadgeLabel(text = "Đang thi", textColor = color.green, bgColor = color.lightGreen)
        SubjectName(name = item.subjectName, color = color.gray, isGoing = true)
        ExamInfo(item = item, color = color, isGoing = true)
    }
}

@Composable
fun ExamNext(
    modifier: Modifier = Modifier,
    color: ExtendedColors,
    item: ExamSchedule,
    isFirstItem: Boolean = false
) {
    ScheduleItemLayout(
        modifier = modifier,
        color = color,
        startTime = item.startTime,
        endTime = item.endTime,
        isGoing = false,
        isFirstItem = isFirstItem
    ) {
        BadgeLabel(text = "Sắp thi", textColor = color.red, bgColor = color.redLight)
        SubjectName(name = item.subjectName, color = color.gray, isGoing = false)
        ExamInfo(item = item, color = color, isGoing = false)
    }
}