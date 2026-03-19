package org.example.project.presentations.screen.gpa_predict.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import org.example.project.presentations.components.LabelView
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun SubjectSectionHeader(
    subjectCount: Int,
    modifier: Modifier = Modifier
) {
    val color = LocalExtendedColors.current

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Học phần học kỳ này",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
        )

        LabelView(
            text = "$subjectCount HỌC PHẦN",
            backgroundColor = color.gray.copy(alpha = 0.1f),
            textColor = color.gray
        )
    }
}