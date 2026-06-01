package org.example.project.presentations.screen.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.DaySchedule
import org.example.project.domain.model.ScheduleType
import org.example.project.presentations.components.shimmerEffect
import org.example.project.presentations.theme.ExtendedColors

@Composable
fun ScheduleClassList(
    modifier: Modifier = Modifier,
    color: ExtendedColors,
    isLoading: Boolean = false,
    daySchedule: List<DaySchedule>,
    onClickViewTomorrow: () -> Unit = {},
) {
    Column {
        Row(
            modifier = modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Lịch hôm nay",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
        }

        when {
            isLoading -> {
                Column(modifier = modifier.padding(top = 10.dp)) {
                    repeat(2) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(top = 3.dp),
                                horizontalAlignment = Alignment.End
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(55.dp)
                                        .height(16.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .shimmerEffect()
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .width(55.dp)
                                        .height(14.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .shimmerEffect()
                                )
                            }

                            Spacer(modifier = Modifier.width(34.dp))

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(90.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .shimmerEffect()
                            )
                        }
                    }
                }
            }

            (daySchedule.isEmpty()) -> {
                ScheduleEmptyCard(
                    isVisibleButton = false,
                    onClickViewTomorrow = onClickViewTomorrow,
                    color = color,
                    title = "Không có hoạt động nào hôm nay",
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            else -> {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    daySchedule.forEach { schedule ->
                        DayScheduleCard(schedule = schedule)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScheduleClassListPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        ScheduleClassList(
            isLoading = true,
            daySchedule = emptyList(),
            color = ExtendedColors(),
        )

        HorizontalDivider()

        ScheduleClassList(
            isLoading = false,
            daySchedule = emptyList(),
            color = ExtendedColors(),
        )

        HorizontalDivider()

        ScheduleClassList(
            isLoading = false,
            color = ExtendedColors(),
            daySchedule = listOf(
                DaySchedule(
                    nameSubject = "The Basic of Typography II",
                    location = "Room C1, Faculty of Art & Design",
                    startTime = "07:00",
                    endTime = "23:59",
                    type = ScheduleType.STUDY,
                    lecturerName = "Gabriel Sutton",
                ),
                DaySchedule(
                    nameSubject = "Design Psychology: Principle of Perception",
                    location = "Room C1, Faculty of Art & Design",
                    startTime = "09:30",
                    endTime = "11:00",
                    type = ScheduleType.STUDY,
                    lecturerName = "Jessie Reeves",
                ),
                DaySchedule(
                    nameSubject = "Midterm Exam – Visual Communication",
                    location = "Hall B2, Faculty of Art & Design",
                    startTime = "13:00",
                    endTime = "15:00",
                    type = ScheduleType.EXAM,
                    examType = "Tự luận",
                ),
            ),
        )
    }
}