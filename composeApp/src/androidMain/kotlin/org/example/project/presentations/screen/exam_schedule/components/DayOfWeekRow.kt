package org.example.project.presentations.screen.exam_schedule.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun DayOfWeekRow() {
    val labels = listOf("Th 2", "Th 3", "Th 4", "Th 5", "Th 6", "Th 7", "CN")
    val color = LocalExtendedColors.current
    Row(modifier = Modifier.fillMaxWidth()) {
        labels.forEachIndexed { idx, label ->
            Text(
                text = label,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Medium,
                    color = if (idx == 6) color.red else color.gray,
                    fontSize = 12.sp
                )
            )
        }
    }
}