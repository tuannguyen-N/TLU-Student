package org.example.project.presentations.screen.exam_schedule.components

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.LibraryBooks
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.data.remote.dto.exam_schedule.ExamSchedule
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun ExamCard(
    exam: ExamSchedule,
    isPast: Boolean,
    isToday: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        LocalExtendedColors.current.gray.copy(alpha = 0.1f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.LibraryBooks,
                    contentDescription = null,
                    tint = if (isPast) LocalExtendedColors.current.gray else if (isToday) LocalExtendedColors.current.green else Color.Black,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = exam.examType,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = if(isToday)LocalExtendedColors.current.orange else LocalExtendedColors.current.gray
                    )
                )
                Text(
                    text = buildAnnotatedString {
                        append(exam.subjectName)
                        append(" - ")
                        withStyle(
                            style = SpanStyle(
                                color = LocalExtendedColors.current.gray,
                                fontSize = 12.sp
                            )
                        ) {
                            append(exam.subjectCode)
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = if (isPast) LocalExtendedColors.current.gray else Color.Black
                    )
                )

                Spacer(Modifier.height(5.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AccessTimeFilled,
                            contentDescription = null,
                            tint = if (isToday) LocalExtendedColors.current.red else LocalExtendedColors.current.gray,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "${exam.startTime} - ${exam.endTime}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = LocalExtendedColors.current.gray
                            )
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = null,
                            tint = if (isToday) LocalExtendedColors.current.red else LocalExtendedColors.current.gray,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "Phòng: ${exam.examRoom}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = LocalExtendedColors.current.gray
                            )
                        )
                    }
                }
            }

            if (!isToday && !isPast) {
                RemainingDay(
                    contentColor = LocalExtendedColors.current.orange,
                    dayLeft = 10,
                )
            }
        }
    }
}