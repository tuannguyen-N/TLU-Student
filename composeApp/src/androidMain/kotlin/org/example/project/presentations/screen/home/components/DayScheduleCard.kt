package org.example.project.presentations.screen.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.data.mapper.toHourMinuteAmPm
import org.example.project.domain.model.DaySchedule
import org.example.project.domain.model.ScheduleType

@Composable
fun DayScheduleCard(
    modifier: Modifier = Modifier,
    schedule: DaySchedule,
) {
    val cardAlpha = if (schedule.isCurrent) 1f else 0.5f

    Row(
        modifier = modifier
            .fillMaxWidth()
            .alpha(cardAlpha),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Card(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (schedule.isCurrent)
                    Color.White
                else
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = if (schedule.isCurrent) 2.dp else 0.dp,
            ),
            border = if (schedule.type == ScheduleType.EXAM)
                BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = if (schedule.isCurrent) 1f else 0.5f))
            else null,
        ) {
            Box {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = schedule.startTime.toHourMinuteAmPm(),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            text = schedule.endTime.toHourMinuteAmPm(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }

                    VerticalDivider()

                    Column(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        Text(
                            text = schedule.nameSubject,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = if (schedule.type == ScheduleType.EXAM)
                                MaterialTheme.colorScheme.error
                            else
                                MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.LocationOn,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                            Text(
                                text = schedule.location,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }

                        if (schedule.type == ScheduleType.STUDY && schedule.lecturerName != null) {
                            LecturerRow(lecturerName = schedule.lecturerName!!)
                        }

                        if (schedule.type == ScheduleType.EXAM && schedule.examType != null) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Edit,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = MaterialTheme.colorScheme.error,
                                )
                                Text(
                                    text = "Kiểm tra ${schedule.examType!!}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.error,
                                )
                            }
                        }
                    }
                }

                if (schedule.isCurrent) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF4CAF50))
                    )
                }
            }
        }
    }
}

@Composable
private fun LecturerRow(
    lecturerName: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = lecturerName
                    .split(" ")
                    .mapNotNull { it.firstOrNull()?.toString() }
                    .take(2)
                    .joinToString(""),
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 6.sp),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.Bold,
            )
        }

        Text(
            text = lecturerName,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DayScheduleCardPreview() {
    Column(
    ) {
        DayScheduleCard(
            schedule = DaySchedule(
                nameSubject = "The Basic of Typography II",
                location = "Room C1, Faculty of Art & Design...",
                startTime = "07:00",
                endTime = "23:59",
                type = ScheduleType.STUDY,
                lecturerName = "Gabriel Sutton",
            )
        )
        DayScheduleCard(
            schedule = DaySchedule(
                nameSubject = "Design Psychology: Principle of...",
                location = "Room C1, Faculty of Art & Design...",
                startTime = "09:30",
                endTime = "10:00",
                type = ScheduleType.STUDY,
                lecturerName = "Jessie Reeves",
            )
        )
        DayScheduleCard(
            schedule = DaySchedule(
                nameSubject = "Midterm Exam – Visual Communication",
                location = "Hall B2, Faculty of Art & Design",
                startTime = "13:00",
                endTime = "15:00",
                type = ScheduleType.EXAM,
                examType = "Tự luận",
            )
        )
    }
}