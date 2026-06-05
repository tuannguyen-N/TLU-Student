package org.example.project.presentations.screen.messages.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.presentations.theme.LocalExtendedColors
import org.example.project.presentations.utils.DateTimeUtils.formatLastSeen

@Preview(showBackground = true)
@Composable
fun AvatarWithPresence(
    avatarUrl: String? = null,
    name: String = "Nguyễn Văn Tày",
    isOnline: Boolean = false,
    lastSeen: Long = 1780632611156,
    size: Int = 56
) {

    Box(modifier = Modifier.size(size.dp)) {
        AvatarImage(
            avatarUrl = avatarUrl,
            name = name,
            size = size
        )

        if (isOnline) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(14.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF4CAF50))
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.surface,
                        shape = CircleShape
                    )
            )
        } else {
            formatLastSeen(lastSeen)?.let {
                Text(
                    text = it,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(x = 8.dp, y = 2.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(LocalExtendedColors.current.lightGray)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 5.dp),
                    fontSize = 10.sp,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Medium,
                    color = LocalExtendedColors.current.gray,
                    maxLines = 1
                )
            }
        }
    }
}