package org.example.project.presentations.screen.transcript_term.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.R
import org.example.project.presentations.theme.LocalExtendedColors
import org.example.project.presentations.utils.isPass
import org.example.project.presentations.utils.toAcademicRank
import org.example.project.presentations.utils.toColor

@Preview
@Composable
fun SubjectResultCard(
    modifier: Modifier = Modifier,
    subjectCode: String = "IT2110",
    subjectName: String = "Lập trình Android",
    attendanceScore: Double = 8.5,
    midtermScore: Double = 8.5,
    finalScore: Double = 8.5,
    score10: Double = 8.5,
    score4: Double = 3.5,
    letterGrade: String ="B"
) {
    val color = score4.toAcademicRank().toColor()

    var isExpanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f
    )

    Box(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                clip = false
            )
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .background(Color.White)
            .padding(12.dp)
    ) {

        Image(
            painterResource(
                if (score4.isPass())
                    R.drawable.icon_pass else R.drawable.icon_fail
            ),
            contentDescription = null,
            modifier = Modifier.align(Alignment.TopEnd)
        )

        Column(
            modifier = Modifier
        ) {
            SubjectCode(subjectCode)

            Text(
                text = subjectName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 5.dp)
            )

            HorizontalDivider(
                thickness = 0.5.dp,
                color = LocalExtendedColors.current.gray,
            )

            Row(
                modifier = Modifier.padding(top = 15.dp),
                horizontalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Information(
                    title = "ĐIỂM HỆ 4",
                    value = score4.toString(),
                    color = color,
                )

                Information(
                    title = "ĐIỂM HỆ 10",
                    value = score10.toString(),
                    color = color,
                )

                Information(
                    title = "ĐIỂM CHỮ",
                    value = letterGrade,
                    color = color,
                )

                Information(
                    title = "SỐ TÍN",
                    value = "02",
                    color = Color.Black,
                )

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = {
                        isExpanded = !isExpanded
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_down),
                        contentDescription = null,
                        modifier = Modifier
                            .size(18.dp)
                            .rotate(rotation),
                        tint = LocalExtendedColors.current.gray,
                    )
                }
            }

            if (isExpanded) Spacer(modifier = Modifier.padding(vertical = 5.dp))

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))

                    DetailInformation(
                        title = "Điểm chuyên cần (10%)",
                        value = attendanceScore.toString(),
                        color = color,
                    )

                    DetailInformation(
                        title = "Điểm giữa kỳ (30%)",
                        value = midtermScore.toString(),
                        color = color,
                    )

                    DetailInformation(
                        title = "Điểm cuối kỳ (60%)",
                        value = finalScore.toString(),
                        color = color,
                    )
                }
            }
        }
    }
}

@Composable
fun DetailInformation(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .size(5.dp)
                .clip(
                    CircleShape
                )
                .background(LocalExtendedColors.current.gray)

        )

        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = LocalExtendedColors.current.gray,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        )

        Text(
            text = value,
            color = color,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.bodyLarge,
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
            style = MaterialTheme.typography.labelSmall,
            color = LocalExtendedColors.current.gray,
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

@Composable
fun SubjectCode(
    name: String,
    modifier: Modifier = Modifier
) {
    val color = LocalExtendedColors.current.gray
    Box(
        modifier = modifier
            .clip(
                RoundedCornerShape(14.dp)
            )
            .background(color.copy(alpha = 0.4f))
            .padding(horizontal = 10.dp, vertical = 2.dp)
    ) {
        Text(
            text = name,
            color = color,
            style = MaterialTheme.typography.labelSmall,
        )
    }
}