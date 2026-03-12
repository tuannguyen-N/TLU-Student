package org.example.project.presentations.screen.exam_schedule.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.core.DayPosition
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun CalendarDayCell(
    day: com.kizitonwose.calendar.core.CalendarDay,
    isSelected: Boolean,
    isToday: Boolean,
    hasExam: Boolean,
    onClick: () -> Unit
) {
    val color = LocalExtendedColors.current
    val isCurrentMonth = day.position == DayPosition.MonthDate
    val isSunday = day.date.dayOfWeek == DayOfWeek.SUNDAY

    val textColor = when {
        !isCurrentMonth -> color.grayButton
        isSelected -> Color.White
        isSunday -> color.red
        else -> Color(0xFF1A1A2E)
    }

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .clip(CircleShape)
            .background(if (isSelected) LocalExtendedColors.current.fontBlue else Color.Transparent)
            .clickable(enabled = isCurrentMonth) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.date.day.toString(),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = if (isSelected || isToday) FontWeight.Bold else FontWeight.Normal,
                color = textColor
            ),
            modifier = Modifier.align(Alignment.Center)
        )

        if (hasExam) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.Bottom
            ) {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) Color.White else LocalExtendedColors.current.fontBlue)
                )

                Spacer(Modifier.height(5.dp))
            }
        }
    }
}

