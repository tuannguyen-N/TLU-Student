package org.example.project.presentations.screen.exam_schedule.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number
import org.example.project.domain.model.ExamDay
import org.example.project.presentations.components.LabelView
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun SelectedDaySummary(
    date: LocalDate,
    examInfo: ExamDay?
) {
    val dayNames =
        listOf("Thứ Hai", "Thứ Ba", "Thứ Tư", "Thứ Năm", "Thứ Sáu", "Thứ Bảy", "Chủ Nhật")
    val dayName = dayNames[date.dayOfWeek.ordinal]
    val dateText = "$dayName, ${date.day.toString().padStart(2, '0')}/${
        date.month.number.toString().padStart(2, '0')
    }/${date.year}"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = dateText,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
        )

        if (examInfo != null) {
            LabelView(
                text = "Môn thi",
                backgroundColor = LocalExtendedColors.current.fontBlue.copy(alpha = 0.15f),
                textColor = LocalExtendedColors.current.fontBlue
            )
        }
    }
}