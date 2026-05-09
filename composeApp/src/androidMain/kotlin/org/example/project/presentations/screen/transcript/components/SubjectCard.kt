package org.example.project.presentations.screen.transcript.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.presentations.components.LabelView
import org.example.project.presentations.theme.LocalExtendedColors
import org.example.project.presentations.utils.toAcademicRank
import org.example.project.presentations.utils.toColor
import org.example.project.presentations.utils.toTextTermRank

@Composable
fun SubjectCard(
    modifier: Modifier = Modifier,
    academicYear: String,
    termNumber: String,
    subjects: List<String>,
    gpa: Double,
    credits: Int,
    onOpenTranscriptTerm: () -> Unit
) {
    val gpa = gpa * 10 / 4
    val color = gpa.toAcademicRank().toColor()
    val rank = gpa.toTextTermRank()
    val maxVisible = 3
    val visibleSubjects = subjects.take(maxVisible)
    val remaining = subjects.size - maxVisible

    Column(
        modifier = modifier
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(14.dp)
            )
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column {
                Text(
                    text = termNumber,
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp),
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Năm học $academicYear",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(LocalExtendedColors.current.gray.copy(alpha = 0.15f))
                    .clickable(onClick = onOpenTranscriptTerm),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Xem chi tiết",
                    tint = LocalExtendedColors.current.gray,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Information(
                title = "GPA",
                value = gpa.toString(),
                color = color,
                modifier = Modifier.weight(1f)
            )

            Information(
                title = "TÍN CHỈ",
                value = credits.toString(),
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )

            Column(modifier = Modifier.weight(1.5f)) {
                Text(
                    text = "XẾP LOẠI",
                    style = MaterialTheme.typography.labelMedium,
                    color = LocalExtendedColors.current.grayNavy,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(Modifier.height(6.dp))
                LabelView(
                    text = rank,
                    backgroundColor = color.copy(alpha = 0.15f),
                    textColor = color
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        HorizontalDivider(
            thickness = 0.5.dp,
            color = LocalExtendedColors.current.gray
        )

        Spacer(Modifier.height(12.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            visibleSubjects.forEach { subject ->
                LabelView(
                    text = subject,
                    backgroundColor = LocalExtendedColors.current.gray.copy(alpha = 0.15f),
                    textColor = LocalExtendedColors.current.gray
                )
            }
            if (remaining > 0) {
                LabelView(
                    text = "+$remaining môn khác",
                    backgroundColor = LocalExtendedColors.current.gray.copy(alpha = 0.15f),
                    textColor = LocalExtendedColors.current.gray
                )
            }
        }
    }
}

@Composable
fun Information(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = LocalExtendedColors.current.grayNavy,
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = color,
        )
    }
}