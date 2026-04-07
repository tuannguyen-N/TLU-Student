package org.example.project.presentations.screen.notification.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.NotificationUiModel
import org.example.project.presentations.theme.ExtendedColors
import org.example.project.presentations.utils.avatarRes
import org.example.project.presentations.utils.iconRes

@Composable
fun NotificationItem(
    modifier: Modifier = Modifier,
    color: ExtendedColors,
    onHoldItem: () -> Unit = {},
    notification: NotificationUiModel
) {
    val backgroundColor = if (notification.isRead)
        color.background
    else
        color.white

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = 20.dp, vertical = 13.dp)
    ) {

        AvatarNotification(
            avatarRes = notification.avatarRes(),
            iconRes = notification.iconRes(),
            isRead = notification.isRead,
            color = color,
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        Column(
            modifier = Modifier
                .padding(start = 15.dp, end = 10.dp)
                .weight(1f)
        ) {
            Text(
                text = notification.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = notification.sender.value,
                style = MaterialTheme.typography.bodyMedium,
                color = color.gray,
            )
        }

        Text(
            text = notification.createdAgo,
            style = MaterialTheme.typography.bodyMedium,
            color = color.gray,
        )
    }
}

@Composable
fun AvatarNotification(
    modifier: Modifier,
    color: ExtendedColors,
    avatarRes: Int,
    iconRes: Int,
    isRead: Boolean = false
) {
    Box(
        modifier = modifier.wrapContentSize()
    ) {

        if (!isRead) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .align(Alignment.TopStart)
                    .background(color.red, CircleShape)
            )
        }

        Image(
            painter = painterResource(avatarRes),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(color.gray)
        )

        Image(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .align(Alignment.BottomEnd)
        )
    }
}