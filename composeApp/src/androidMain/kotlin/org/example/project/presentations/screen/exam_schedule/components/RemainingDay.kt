package org.example.project.presentations.screen.exam_schedule.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun RemainingDay(
    contentColor: Color,
    dayLeft: String,
    modifier: Modifier = Modifier
) {
    val isToday = dayLeft == "Hôm nay"

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(contentColor.copy(alpha = 0.15f))
            .padding(vertical = 5.dp, horizontal = 8.dp)
    ) {
        if (isToday) {
            Text(
                text = "Hôm nay",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                color = contentColor
            )
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = dayLeft,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = contentColor
                )
            }
        }
    }
}