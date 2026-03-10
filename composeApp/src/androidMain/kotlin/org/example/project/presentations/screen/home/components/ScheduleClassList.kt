package org.example.project.presentations.screen.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.data.remote.dto.schedule.CourseClass
import org.example.project.presentations.components.shimmerBrush
import org.example.project.presentations.theme.LocalExtendedColors
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
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    repeat(2) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .padding(bottom = 10.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(shimmerBrush())
                        )
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