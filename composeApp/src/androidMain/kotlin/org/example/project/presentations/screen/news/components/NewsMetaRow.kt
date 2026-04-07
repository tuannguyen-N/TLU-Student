package org.example.project.presentations.screen.news.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.presentations.theme.ExtendedColors

@Composable
fun NewsMetaRow(
    color: ExtendedColors,
    timeAgo: String,
    compact: Boolean = false
) {
    val iconSize = if (compact) 12.dp else 14.dp
    val textStyle = if (compact)
        MaterialTheme.typography.labelSmall
    else
        MaterialTheme.typography.labelMedium

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Schedule,
                contentDescription = null,
                tint = color.white.copy(alpha = 0.85f),
                modifier = Modifier.size(iconSize)
            )
            Text(
                text = timeAgo,
                style = textStyle,
                color = color.white.copy(alpha = 0.85f)
            )
        }
    }
}