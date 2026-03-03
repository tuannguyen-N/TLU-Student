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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.Notification
import org.example.project.domain.model.NotificationType
import org.example.project.presentations.theme.LocalExtendedColors
import org.example.project.presentations.utils.avatarRes
import org.example.project.presentations.utils.iconRes
import org.example.project.presentations.utils.toFormatTime

@Preview
@Composable
fun NotificationItem(
    modifier: Modifier = Modifier,
    onHoldItem: () -> Unit = {},
    notification: Notification = Notification(
        "Demo1231232@",
        "Demo",
        "Demo",
        NotificationType.SCHOOL,
        1620972800
    )
) {
    val backgroundColor = if (notification.isRead)
        LocalExtendedColors.current.background
    else
        LocalExtendedColors.current.white

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
                text = notification.actor,
                style = MaterialTheme.typography.bodyMedium,
                color = LocalExtendedColors.current.gray,
            )
        }

        Text(
            text = notification.notificationTime.toFormatTime(),
            style = MaterialTheme.typography.bodyMedium,
            color = LocalExtendedColors.current.gray,
        )
    }
}

@Composable
fun AvatarNotification(
    modifier: Modifier,
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
                    .background(LocalExtendedColors.current.red, CircleShape)
            )
        }

        Image(
            painter = painterResource(avatarRes),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
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