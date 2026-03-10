package org.example.project.presentations.screen.school_schedule.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.example.project.R
import org.example.project.data.remote.dto.day_schedule.CourseClass
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun SubjectInformationCard(
    modifier: Modifier = Modifier,
    courseClass: CourseClass,
    isOngoing: Boolean = true,
    onClick: () -> Unit = {}
) {
    val backgroundColor = if (isOngoing) LocalExtendedColors.current.mainBlue else Color.White
    val primaryTextColor = if (isOngoing) Color.White else Color.Black
    val secondaryTextColor = if (isOngoing) Color(0xFFCFD3F5) else LocalExtendedColors.current.gray

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 4.dp,
        onClick = onClick,
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = courseClass.subjectName,
                    color = primaryTextColor,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                CardState(
                    isOngoing = isOngoing,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

            Text(
                text = courseClass.room,
                color = secondaryTextColor,
                style = MaterialTheme.typography.bodyMedium,
            )

            InformationView(
                iconRes = R.drawable.icon_clock,
                value = "${courseClass.startTime} - ${courseClass.endTime}",
                color = secondaryTextColor,
                modifier = Modifier.padding(top = 10.dp)
            )

            InformationView(
                iconRes = R.drawable.icon_teacher,
                value = courseClass.lecturerName,
                color = secondaryTextColor,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }
}

@Composable
fun InformationView(
    iconRes: Int,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = color,
            modifier = Modifier
                .padding(end = 4.dp)
                .size(14.dp),
        )

        Text(
            text = value,
            color = color,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun CardState(
    modifier: Modifier = Modifier,
    isOngoing: Boolean,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(
                if (isOngoing) Color(0x6616A634) else Color(0x66848484)
            )
            .padding(horizontal = 7.dp, vertical = 2.dp)
    ) {
        Text(
            text = if (isOngoing) "Đang diễn ra" else "Sắp diễn ra",
            style = MaterialTheme.typography.labelSmall,
            color = if (isOngoing) Color(0xFF16A634) else Color(0xFF848484)
        )
    }
}
