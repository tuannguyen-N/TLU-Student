package org.example.project.presentations.screen.transcript.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.R
import org.example.project.presentations.theme.LocalExtendedColors
import org.example.project.presentations.utils.toAcademicRank
import org.example.project.presentations.utils.toColor
import org.example.project.presentations.utils.toTextRank

@Preview
@Composable
fun GpaCard(
    modifier: Modifier = Modifier,
    gpa: Double = 1.0,
    credit: Int = 36
) {
    val rank = gpa.toAcademicRank()
    val textColor = rank.toColor()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(14.dp),
                clip = false
            )
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .padding(vertical = 18.dp, horizontal = 40.dp)
    ) {
        Text(
            text = "GPA",
            color = LocalExtendedColors.current.gray,
            style = MaterialTheme.typography.titleMedium
        )

        Row(modifier = Modifier.padding(top = 5.dp, bottom = 10.dp)) {
            Text(
                text = gpa.toString(),
                fontWeight = FontWeight.Bold,
                color = textColor,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 30.sp
            )

            Text(
                text = "/4.0",
                color = LocalExtendedColors.current.gray,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(start = 4.dp, bottom = 2.dp)
                    .align(Alignment.Bottom)
            )
        }

        RankAchievement(gpa = gpa)

        Text(
            text = buildAnnotatedString {
                append("Đã tích luỹ: ")

                withStyle(
                    style = SpanStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append("$credit")
                }
                append("/136 tín chỉ")
            },
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 12.dp),
            color = LocalExtendedColors.current.gray
        )
    }
}

@Composable
fun RankAchievement(
    gpa: Double
){
    val color = gpa.toAcademicRank().toColor()

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .background(color.copy(alpha = 0.4f))
            .padding(horizontal = 10.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.icon_ranking),
            contentDescription = null,
            tint = color
        )

        Text(
            text = "Sinh viên ${gpa.toTextRank()}",
            color = color,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}