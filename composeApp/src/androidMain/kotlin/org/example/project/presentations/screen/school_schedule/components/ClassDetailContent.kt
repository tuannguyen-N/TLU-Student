package org.example.project.presentations.screen.school_schedule.components

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
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.MeetingRoom
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.R
import org.example.project.data.remote.dto.day_schedule.CourseClass
import org.example.project.presentations.components.ButtonView
import org.example.project.presentations.screen.transcript_term.components.SubjectCode
import org.example.project.presentations.theme.LocalExtendedColors
import org.example.project.presentations.utils.toHourMinute

@Composable
fun ClassDetailContent(
    isGoing: Boolean = false,
    courseClass: CourseClass,
    onViewMaterials: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = if (isGoing) LocalExtendedColors.current.greenLight else LocalExtendedColors.current.gray.copy(
                            alpha = 0.3f
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 5.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                color = if (isGoing) LocalExtendedColors.current.green else LocalExtendedColors.current.gray,
                                shape = CircleShape
                            )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isGoing) "Đang diễn ra" else "Chưa diễn ra",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isGoing) LocalExtendedColors.current.green else LocalExtendedColors.current.gray,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            SubjectCode(courseClass.subjectCode)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = courseClass.subjectName,
            style = MaterialTheme.typography.headlineSmall,
            color = LocalExtendedColors.current.mainBlue,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            InfoCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Outlined.MeetingRoom,
                label = "PHÒNG HỌC",
                primaryText = courseClass.room,
                secondaryText = "Toà ${courseClass.room.first().uppercase()}"
            )

            InfoCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Outlined.AccessTime,
                label = "THỜI GIAN",
                primaryText = "${courseClass.startTime.toHourMinute()} - ${courseClass.endTime.toHourMinute()}",
                secondaryText = "Ca ${courseClass.startPeriod} - ${courseClass.endPeriod}"
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        LecturerCard(
            lecturerName = courseClass.lecturerName,
            lecturerId = "",
            lectureEmail = courseClass.lecturerEmail
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "NỘI DUNG BÀI HỌC",
            style = MaterialTheme.typography.labelMedium,
            color = LocalExtendedColors.current.mainBlue,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Nothing", // TODO: add lesson content
            style = MaterialTheme.typography.bodyMedium,
            color = LocalExtendedColors.current.gray,
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        ButtonView(
            text = "Xem tài liệu học tập",
            backgroundColorRes = LocalExtendedColors.current.gray.copy(alpha = 0.12f),
            textColorRes = LocalExtendedColors.current.mainBlue,
            onClick = onViewMaterials,
            enabled = true,
            iconRes = R.drawable.icon_adress,
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}
