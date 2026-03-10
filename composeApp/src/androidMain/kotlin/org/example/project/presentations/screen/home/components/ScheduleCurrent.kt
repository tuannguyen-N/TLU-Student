package org.example.project.presentations.screen.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.R
import org.example.project.data.remote.dto.day_schedule.CourseClass
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun ScheduleCurrent(
    modifier: Modifier = Modifier,
    item: CourseClass
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(top = 3.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "07:30 am",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = "09:30 am",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
            )
        }

        Spacer(modifier = Modifier.width(34.dp))

        val dotColor = LocalExtendedColors.current.green
        val lineColor = LocalExtendedColors.current.gray

        Card(
            modifier = Modifier
                .weight(1f)
                .drawBehind {
                    val dotRadius = 3.5.dp.toPx()
                    val startX = -12.dp.toPx() - dotRadius
                    val topOffset = 7.dp.toPx()
                    drawCircle(
                        color = dotColor,
                        radius = dotRadius,
                        center = androidx.compose.ui.geometry.Offset(startX, topOffset + dotRadius)
                    )
                    drawLine(
                        color = lineColor,
                        start = androidx.compose.ui.geometry.Offset(
                            startX,
                            topOffset + dotRadius * 2
                        ),
                        end = androidx.compose.ui.geometry.Offset(startX, size.height),
                        strokeWidth = 1.dp.toPx()
                    )
                },
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(2.dp),
        ) {
            Column(
                modifier = Modifier
                    .background(LocalExtendedColors.current.white)
                    .padding(horizontal = 12.dp, vertical = 10.dp)
                    .fillMaxWidth()

            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(LocalExtendedColors.current.greenLight)
                        .padding(horizontal = 12.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "Đang diễn ra",
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.bodySmall,
                        color = LocalExtendedColors.current.green,
                    )
                }

                Text(
                    text = item.subjectName,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 5.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.icon_location),
                        contentDescription = "location"
                    )

                    Text(
                        text = item.room,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Normal,
                        color = LocalExtendedColors.current.gray,
                        modifier = Modifier.padding(horizontal = 3.dp)
                    )

                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .size(3.dp)
                            .background(
                                color = LocalExtendedColors.current.red,
                                shape = CircleShape
                            )
                    )

                    Text(
                        text = "Toà ${item.room.first()}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Normal,
                        color = LocalExtendedColors.current.gray,
                        modifier = Modifier.padding(horizontal = 3.dp)
                    )
                }
            }
        }
    }
}