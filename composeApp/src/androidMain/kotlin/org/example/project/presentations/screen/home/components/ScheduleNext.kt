package org.example.project.presentations.screen.home.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.datetime.LocalTime
import org.example.project.data.mapper.getStatusText
import org.example.project.data.remote.dto.week_schedule.CourseClass
import org.example.project.presentations.theme.ExtendedColors

@Composable
fun ScheduleNext(
    modifier: Modifier = Modifier,
    color: ExtendedColors,
    item: CourseClass,
    currentTime: LocalTime,
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
        BadgeLabel(text = item.getStatusText(currentTime), textColor = color.red, bgColor = color.redLight)
        SubjectName(name = item.subjectName, color = color.gray, isGoing = false)
        RoomInfo(room = item.room, color = color, isGoing = false)
    }
}