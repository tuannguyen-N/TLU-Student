package org.example.project.presentations.screen.messages.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun AvatarImage(avatarUrl: String?, name: String, size: Int) {
    val color = LocalExtendedColors.current
    if (avatarUrl != null) {
        AsyncImage(
            model = avatarUrl,
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(size.dp)
                .clip(CircleShape)
        )
    } else {
        Box(
            modifier = Modifier
                .size(size.dp)
                .clip(CircleShape)
                .background(color.lightGray),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name.take(1).uppercase(),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = color.mainBlue
            )
        }
    }
}