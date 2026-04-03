package org.example.project.presentations.screen.tuition_payment.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.data.mapper.toFormatAmountAndD
import org.example.project.data.remote.dto.tuition_detail.TuitionItem
import org.example.project.presentations.theme.ExtendedColors
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun DetailTuitionCourse(modifier: Modifier = Modifier, color: ExtendedColors, items: List<TuitionItem> ) {
    LocalExtendedColors.current
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Chi tiết môn học",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "5 môn học",
                style = MaterialTheme.typography.titleSmall
            )
        }

        Spacer(Modifier.height(16.dp))

        TuitionCourseList(
            color = color,
            courses = items
        )
    }
}


@Composable
private fun TuitionCourseList(
    modifier: Modifier = Modifier,
    color: ExtendedColors,
    courses: List<TuitionItem>
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = color.white
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            courses.forEach { course ->
                TuitionCourseItem(
                    courseCode = "course.code", // TODO:
                    courseName = course.subjectName,
                    credits = course.credits,
                    amount = course.amount.toLong().toFormatAmountAndD(),
                    color = color,
                    isLastItem = courses.last() == course
                )
            }
        }
    }
}