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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.data.remote.dto.week_schedule.CourseClass
import org.example.project.data.remote.dto.week_schedule.Lecturer
import org.example.project.presentations.screen.home.components.ScheduleEmptyCard
import org.example.project.presentations.theme.ExtendedColors

@Composable
fun TodayScheduleList(
    color: ExtendedColors,
    modifier: Modifier = Modifier,
    courseClasses: List<CourseClass> = emptyList(),
    isToday: Boolean = false,
    daysUntil: Int = 0,
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

        if (courseClasses.isNotEmpty()) {
            LazyColumn(modifier = Modifier) {
                items(courseClasses) { courseClass ->
                    ScheduleItem(
                        modifier = Modifier,
                        courseClass = courseClass,
                        isToday = isToday,
                        daysUntil = daysUntil,
                        onOpenDetailCourseClass = { onOpenDetailCourseClass(courseClass) }
                    )
                }
            }
        } else {
            ScheduleEmptyCard(
                color = color,
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
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
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

@Preview(showBackground = true)
@Composable
fun TodayScheduleListPreview() {
    val courseClasses = listOf(
        CourseClass(
            classCode = "INT2204 1",
            dayOfWeek = 2,
            subjectName = "Lập trình Android",
            subjectCode = "INT2204",
            credits = 3,
            startPeriod = 1,
            endPeriod = 3,
            startTime = "07:00:00",
            endTime = "09:30:00",
            room = "A305",
            lecturer = Lecturer(
                lecturerCode = "GV001",
                fullName = "Nguyễn Văn A",
                phoneNumber = "0123456789",
                email = "nguyenvana@example.com"
            )
        ),
        CourseClass(
            classCode = "INT2215 2",
            dayOfWeek = 2,
            subjectName = "Cơ sở dữ liệu",
            subjectCode = "INT2215",
            credits = 3,
            startPeriod = 4,
            endPeriod = 6,
            startTime = "10:10:00",
            endTime = "12:40:00",
            room = "B201",
            lecturer = Lecturer(
                lecturerCode = "GV002",
                fullName = "Trần Thị B",
                phoneNumber = "0987654321",
                email = "tranthib@example.com"
            )
        )
    )

    MaterialTheme {
        TodayScheduleList(
            color = ExtendedColors(
                mainRed = Color(0xFFE53935),
                red = Color.Red,
                gray = Color.Gray,
                white = Color.White
            ),
            courseClasses = courseClasses,
            isToday = true,
            daysUntil = 0,
            onOpenTimetable = {},
            onOpenDetailCourseClass = {},
            onClickViewTomorrow = {}
        )
    }
}