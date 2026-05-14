package org.example.project.presentations.screen.transcript.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlignVerticalBottom
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.R
import org.example.project.presentations.theme.LocalExtendedColors
import org.example.project.presentations.utils.gpaToAcademicRank
import org.example.project.presentations.utils.toColor
import org.example.project.presentations.utils.toTextRank

@Preview
@Composable
fun GpaCard(
    modifier: Modifier = Modifier,
    gpa: Double = 3.3,
    credit: Int = 36,
    totalCredits: Int = 136,
    onOpenGpaTracker: () -> Unit = {}
) {
    val rank = gpa.gpaToAcademicRank()
    val textColor = rank.toColor()

    Box(
        modifier = modifier
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(14.dp)
            )
            .background(Color.White)

    ) {
        Column(
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 24.dp)
        ) {
            RankAchievement(gpa = gpa)

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Điểm trung bình tích luỹ (GPA)",
                color = LocalExtendedColors.current.grayNavy,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = gpa.toString(),
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    fontSize = 52.sp,
                    lineHeight = 56.sp
                )
                Text(
                    text = "/4.0",
                    color = LocalExtendedColors.current.gray,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Tiến độ học tập",
                color = LocalExtendedColors.current.grayNavy,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                LinearProgressIndicator(
                    progress = { credit.toFloat() / totalCredits.toFloat() },
                    modifier = Modifier
                        .weight(1f)
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = textColor,
                    trackColor = LocalExtendedColors.current.gray.copy(alpha = 0.2f)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "$credit/$totalCredits Tín chỉ",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }
        }

        IconButton(
            onClick = onOpenGpaTracker,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(18.dp)
                .clip(
                    CircleShape
                )
                .background(LocalExtendedColors.current.fontBlue.copy(alpha = 0.1f))
                .size(30.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AlignVerticalBottom,
                contentDescription = null,
                tint = LocalExtendedColors.current.mainBlue,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun RankAchievement(
    gpa: Double
) {
    val color = gpa.gpaToAcademicRank().toColor()

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(color.copy(alpha = 0.15f))
            .padding(horizontal = 12.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.icon_ranking),
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(18.dp)
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = "Sinh viên ${(gpa * 10 / 4).toTextRank()}",
            color = color,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold
        )
    }
}