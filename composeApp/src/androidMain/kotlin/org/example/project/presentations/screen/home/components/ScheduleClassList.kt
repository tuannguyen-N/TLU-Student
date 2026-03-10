package org.example.project.presentations.screen.home.components

import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.data.remote.dto.day_schedule.CourseClass
import org.example.project.presentations.components.shimmerBrush
import org.example.project.presentations.utils.isGoing

@Composable
fun ScheduleClassList(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    onClickViewTomorrow: () -> Unit = {},
    courseClasses: List<CourseClass>?
) {
    Column {
        Row(
            modifier = modifier
                .padding(bottom = 15.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Lịch học hôm nay",
                style = MaterialTheme.typography.titleMedium,
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
                                        .background(shimmerBrush())
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .width(55.dp)
                                        .height(14.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(shimmerBrush())
                                )
                            }

                            Spacer(modifier = Modifier.width(34.dp))

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(90.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(shimmerBrush())
                            )
                        }
                    }
                }
            }

            courseClasses.isNullOrEmpty() -> {
                ScheduleEmptyCard(
                    onClickViewTomorrow = onClickViewTomorrow,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            else -> {
                courseClasses.forEach { item ->
                    if (item.isGoing()) {
                        ScheduleCurrent(modifier = modifier, item = item)
                    } else {
                        ScheduleNext(modifier = modifier, item = item)
                    }
                }
            }
        }
    }
}