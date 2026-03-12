package org.example.project.presentations.screen.school_schedule.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import org.example.project.data.remote.dto.week_schedule.CourseClass
import org.example.project.presentations.screen.home.components.ScheduleEmptyCard

@Composable
fun TodayScheduleList(
    modifier: Modifier = Modifier,
    courseClasses: List<CourseClass> = emptyList(),
    onOpenTimetable: () -> Unit = {},
    onOpenDetailCourseClass: (CourseClass) -> Unit = {},
    onClickViewTomorrow: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        TitleView(onOpenTimetable)

        if(!courseClasses.isEmpty()){
            LazyColumn(modifier = Modifier) {
                items(courseClasses) { courseClass ->
                    ScheduleItem(
                        courseClass = courseClass,
                        modifier = Modifier,
                        onOpenDetailCourseClass = {onOpenDetailCourseClass(courseClass)}
                    )
                }
            }
        }else {
            ScheduleEmptyCard(
                onClickViewTomorrow = onClickViewTomorrow,
            )
        }
    }
}

@Composable
fun TitleView(
    onOpenTimetable: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp)
    ) {
        Text(
            text = "Lịch học hôm nay",
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = "Thời khoá biểu",
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF016DB7),
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable(
                onClick = onOpenTimetable
            )
        )
    }
}