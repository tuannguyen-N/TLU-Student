package org.example.project.presentations.screen.home.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.example.project.data.remote.dto.week_schedule.CourseClass
import org.example.project.presentations.theme.ExtendedColors

@Composable
fun ScheduleCurrent(modifier: Modifier = Modifier, color: ExtendedColors, item: CourseClass) {
    ScheduleItemLayout(
        modifier = modifier,
        color = color,
        startTime = item.startTime,
        endTime = item.endTime,
        isGoing = true
    ) {
        BadgeLabel(text = "Đang diễn ra", textColor = color.green, bgColor = color.lightGreen)
        SubjectName(name = item.subjectName, color = color.blackBackground, isGoing = true)
        RoomInfo(room = item.room, color = color, isGoing = true)
    }
}