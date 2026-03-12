package org.example.project.presentations.screen.transcript.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.R
import org.example.project.presentations.theme.LocalExtendedColors
import org.example.project.presentations.utils.toAcademicRank
import org.example.project.presentations.utils.toColor
import org.example.project.presentations.utils.toTextTermRank

@Preview
@Composable
fun SubjectCard(
    modifier: Modifier = Modifier,
    termNumber: String = "1",
    subjects: List<String> = listOf("Toán", "Lý", "Hóa"),
    gpa: Double = 2.2,
    credits: Int = 15,
    onOpenTranscriptTerm: () -> Unit = {}
) {
    val color = gpa.toAcademicRank().toColor()
    val rank = gpa.toTextTermRank()

    Column(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(14.dp),
                clip = false
            )
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .background(Color.White)
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Học kỳ $termNumber",
                color = LocalExtendedColors.current.mainBlue,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
            )

            RankCard(color, rank)
        }

        Spacer(Modifier.height(3.dp))

        Text(
            text = subjects.joinToString(", "),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF66778D)
        )

        Row(
            modifier = Modifier.padding(top = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Information(
                title = "GPA",
                value = gpa.toString(),
                modifier = modifier.padding(end = 25.dp),
                color = color
            )

            Information(
                title = "TÍN CHỈ",
                value = credits.toString(),
                color = Color.Black
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = onOpenTranscriptTerm
            ) {
                Image(
                    painter = painterResource(R.drawable.icon_go_button),
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
fun RankCard(
    color: Color,
    rank: String
) {
    Box(
        modifier = Modifier
            .clip(
                RoundedCornerShape(14.dp)
            )
            .background(color.copy(alpha = 0.4f))
            .padding(horizontal = 10.dp, vertical = 2.dp)
    ) {
        Text(
            text = rank,
            color = color,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Composable
fun Information(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = Color(0xFF66778D),
            fontWeight = FontWeight.SemiBold,
        )

        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = color,
        )
    }
}