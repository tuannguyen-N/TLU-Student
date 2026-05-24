package org.example.project.presentations.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.R
import org.example.project.data.remote.dto.exam_schedule.ExamSchedule
import org.example.project.presentations.theme.ExtendedColors

@Composable
fun BadgeLabel(text: String, textColor: Color, bgColor: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .padding(horizontal = 12.dp, vertical = 3.dp)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.bodySmall,
            color = textColor
        )
    }
}

@Composable
fun SubjectName(name: String, color: Color, isGoing: Boolean) {
    Text(
        text = name,
        fontWeight = if (isGoing) FontWeight.SemiBold else FontWeight.Normal,
        style = MaterialTheme.typography.titleMedium,
        color = color,
        modifier = Modifier.padding(top = 8.dp, bottom = 5.dp)
    )
}

@Composable
fun RoomInfo(room: String, color: ExtendedColors, isGoing: Boolean) {
    val tint = if (isGoing) color.red else color.gray
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(R.drawable.icon_location),
            modifier = Modifier.size(12.dp), tint = tint, contentDescription = null
        )
        Text(
            text = room, style = MaterialTheme.typography.bodySmall,
            color = color.gray, modifier = Modifier.padding(horizontal = 3.dp)
        )
        Box(
            modifier = Modifier
                .padding(2.dp)
                .size(3.dp)
                .background(tint, CircleShape)
        )
        Text(
            text = "Toà ${room.first()}", style = MaterialTheme.typography.bodySmall,
            color = color.gray, modifier = Modifier.padding(horizontal = 3.dp)
        )
    }
}

@Composable
fun ExamInfo(item: ExamSchedule, color: ExtendedColors, isGoing: Boolean) {
    val tint = if (isGoing) color.red else color.gray
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(R.drawable.icon_location),
            modifier = Modifier.size(12.dp), tint = tint, contentDescription = null
        )
        Text(
            text = item.examRoom, style = MaterialTheme.typography.bodySmall,
            color = color.gray, modifier = Modifier.padding(horizontal = 3.dp)
        )
        Box(
            modifier = Modifier
                .padding(2.dp)
                .size(3.dp)
                .background(tint, CircleShape)
        )
        Text(
            text = item.examFormat, style = MaterialTheme.typography.bodySmall,
            color = color.gray, modifier = Modifier.padding(horizontal = 3.dp)
        )
    }
}