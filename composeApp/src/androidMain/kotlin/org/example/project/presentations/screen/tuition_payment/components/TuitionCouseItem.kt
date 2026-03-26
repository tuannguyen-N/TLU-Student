package org.example.project.presentations.screen.tuition_payment.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.presentations.theme.ExtendedColors
import org.example.project.presentations.theme.LocalExtendedColors
import org.example.project.presentations.utils.toFormatAmountAndD

@Composable
fun TuitionCourseItem(
    modifier: Modifier = Modifier,
    color: ExtendedColors,
    courseCode: String,
    courseName: String,
    credits: Int,
    amount: Long,
) {
    Column(modifier = modifier.fillMaxWidth().background(color.white)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = courseCode,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = color.blackBackground
                )

                Row {
                    Text(
                        text = courseName,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = color.blackBackground,
                        modifier = Modifier.weight(1f),
                    )

                    Spacer(Modifier.width(10.dp))

                    Text(
                        text = amount.toFormatAmountAndD(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = color.mainBlue,
                        maxLines = 1
                    )
                }

                Text(
                    text = "$credits Tín chỉ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = color.gray
                )
            }
        }

        HorizontalDivider(
            thickness = 0.5.dp,
            color = color.gray
        )
    }
}

@Composable
fun TuitionCourseList(
    modifier: Modifier = Modifier,
    color: ExtendedColors,
    courses: List<TuitionCourse>
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            courses.forEach { course ->
                TuitionCourseItem(
                    courseCode = course.code,
                    courseName = course.name,
                    credits = course.credits,
                    amount = course.amount,
                    color = color
                )
            }
        }
    }
}

data class TuitionCourse(
    val code: String,
    val name: String,
    val credits: Int,
    val amount: Long
)

@Preview(showBackground = true, backgroundColor = 0xFFF0F0F5)
@Composable
private fun TuitionCourseListPreview() {
        TuitionCourseList(
            modifier = Modifier.padding(16.dp),
            courses = listOf(
                TuitionCourse("CS201", "Cơ sở dữ liệu nâng cao", 4, 5_200_000L),
                TuitionCourse("IT302", "Lập trình hướng đối tượng", 3, 4_500_000L),
                TuitionCourse("CS405", "Phân tích thuật toán", 4, 5_200_000L),
            ),
            color = LocalExtendedColors.current
        )
}