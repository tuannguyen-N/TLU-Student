package org.example.project.presentations.screen.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.project.R
import org.example.project.data.remote.dto.week_schedule.CourseClass
import org.example.project.data.remote.dto.week_schedule.Lecturer
import org.example.project.presentations.theme.LocalExtendedColors
import org.example.project.presentations.theme.Poppins
import org.example.project.data.mapper.getStatusText
import org.example.project.data.mapper.toHourMinuteAmPm
import kotlin.time.Clock

@Composable
fun ScheduleNext(
    modifier: Modifier = Modifier,
    item: CourseClass,
    currentTime: LocalTime,
    isFirstItem: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(top = 15.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = item.startTime.toHourMinuteAmPm(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = LocalExtendedColors.current.gray
            )

            Text(
                text = item.endTime.toHourMinuteAmPm(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                color = LocalExtendedColors.current.gray
            )
        }

        Spacer(modifier = Modifier.width(34.dp))

        val dotColor = LocalExtendedColors.current.gray
        val lineColor = LocalExtendedColors.current.gray

        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(2.dp),
            modifier = Modifier
                .weight(1f)
                .padding(top = 15.dp, bottom = 5.dp)
                .drawBehind {
                    val dotRadius = 3.5.dp.toPx()
                    val startX = -12.dp.toPx() - dotRadius
                    val cardTopOffsetOrig = -15.dp.toPx()
                    val dotTopOffsetOrig = cardTopOffsetOrig + 23.dp.toPx()
                    if (!isFirstItem) {
                        drawLine(
                            color = lineColor,
                            start = androidx.compose.ui.geometry.Offset(startX, cardTopOffsetOrig),
                            end = androidx.compose.ui.geometry.Offset(
                                startX,
                                size.height + 5.dp.toPx()
                            ),
                            strokeWidth = 1.dp.toPx()
                        )
                    } else {
                        drawLine(
                            color = lineColor,
                            start = androidx.compose.ui.geometry.Offset(
                                startX,
                                dotTopOffsetOrig + dotRadius * 2
                            ),
                            end = androidx.compose.ui.geometry.Offset(
                                startX,
                                size.height + 5.dp.toPx()
                            ),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                    drawCircle(
                        color = dotColor,
                        radius = dotRadius,
                        center = androidx.compose.ui.geometry.Offset(
                            startX,
                            dotTopOffsetOrig + dotRadius
                        )
                    )
                }
        ) {
            Column(
                modifier = Modifier
                    .background(Color(0xFFF7F7F7))
                    .padding(horizontal = 12.dp, vertical = 10.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(LocalExtendedColors.current.redLight)
                        .padding(horizontal = 12.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = item.getStatusText(currentTime),
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.bodySmall,
                        color = LocalExtendedColors.current.red,
                    )
                }

                Text(
                    text = item.subjectName,
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = Poppins,
                    color = LocalExtendedColors.current.gray,
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 5.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_location),
                        contentDescription = "location",
                        tint = LocalExtendedColors.current.gray,
                        modifier = Modifier.size(12.dp)
                    )

                    Text(
                        text = item.room,
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.bodySmall,
                        color = LocalExtendedColors.current.gray,
                        modifier = Modifier.padding(horizontal = 3.dp)
                    )

                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .size(3.dp)
                            .background(
                                color = LocalExtendedColors.current.gray,
                                shape = CircleShape
                            )
                    )

                    Text(
                        text = "Toà ${item.room.first()}",
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.bodySmall,
                        color = LocalExtendedColors.current.gray,
                        modifier = Modifier.padding(horizontal = 3.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScheduleNextPreview() {
    val sampleCourse = CourseClass(
        classCode = "INT2204",
        dayOfWeek = 2,
        endPeriod = 5,
        endTime = "10:30:00",
        room = "A101",
        startPeriod = 3,
        startTime = "08:45:00",
        subjectCode = "INT2204",
        subjectName = "Lập trình Android",
        lecturer = Lecturer(
            fullName = "Nguyễn Văn A",
            lecturerCode = "123",
            phoneNumber = "",
            email = "vana@example.com"
        )
    )
    val currentTime = remember {
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
    }
    ScheduleNext(
        item = sampleCourse,
        currentTime = currentTime,
        isFirstItem = true
    )
}