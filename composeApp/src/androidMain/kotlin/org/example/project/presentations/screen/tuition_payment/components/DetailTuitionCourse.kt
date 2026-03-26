package org.example.project.presentations.screen.tuition_payment.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.presentations.theme.ExtendedColors
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun DetailTuitionCourse(modifier: Modifier = Modifier, color: ExtendedColors) {
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
            courses = listOf(
                TuitionCourse("CS201", "Cơ sở dữ liệu nâng cao", 4, 5_200_000L),
                TuitionCourse("IT302", "Lập trình hướng đối tượng", 3, 4_500_000L),
                TuitionCourse("CS405", "Phân tích thuật toán", 4, 5_200_000L),
            ),
            color = color
        )
    }
}