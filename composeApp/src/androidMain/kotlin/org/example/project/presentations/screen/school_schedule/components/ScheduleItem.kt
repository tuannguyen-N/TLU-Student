package org.example.project.presentations.screen.school_schedule.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.R
import org.example.project.data.remote.dto.week_schedule.CourseClass
import org.example.project.presentations.theme.LocalExtendedColors
import org.example.project.presentations.utils.isGoing
import org.example.project.presentations.utils.toHourMinute

@Composable
fun ScheduleItem(
    modifier: Modifier = Modifier,
    courseClass: CourseClass,
    onOpenDetailCourseClass: () -> Unit
) {
    val isOngoing = courseClass.isGoing()
    Row(
        modifier = modifier.height(IntrinsicSize.Min)
    ) {
        TimeView(
            isOngoing = isOngoing,
            shift = courseClass.startPeriod.toString(),
            startTime = courseClass.startTime.toHourMinute()
        )
        ScheduleDotLine(isOngoing = isOngoing, modifier = Modifier.padding(horizontal = 20.dp))
        SubjectInformationCard(
            courseClass = courseClass,
            isOngoing = isOngoing,
            modifier = Modifier.padding(bottom = 25.dp),
            onClick = onOpenDetailCourseClass
        )
    }
}

@Composable
fun ScheduleDotLine(isOngoing: Boolean, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
    ) {
        VerticalDivider(
            thickness = 1.dp,
            color = LocalExtendedColors.current.gray,
            modifier = Modifier.align(Alignment.Center)
        )

        if (isOngoing) {
            Icon(
                painter = painterResource(R.drawable.icon_schedule_dot),
                contentDescription = null,
                tint = LocalExtendedColors.current.red,
                modifier = Modifier.size(14.dp)
            )
        } else {
            Spacer(
                modifier = Modifier
                    .size(7.dp)
                    .clip(CircleShape)
                    .background(LocalExtendedColors.current.gray)
            )
        }
    }
}

@Composable
fun TimeView(
    isOngoing: Boolean,
    shift: String,
    startTime: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Ca $shift",
            style = MaterialTheme.typography.bodyMedium,
            color = LocalExtendedColors.current.gray
        )

        Text(
            text = startTime,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleMedium,
            color = if (isOngoing) Color.Black else LocalExtendedColors.current.gray,
        )
    }
}