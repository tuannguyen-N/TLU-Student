package org.example.project.presentations.screen.notification.components

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.Notification
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun NotificationList(
    onShowBottomSheet: () -> Unit,
    notifications: List<Notification>,
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(notifications) { index, item ->

            NotificationItem(
                notification = item,
                modifier = Modifier.combinedClickable(
                    onLongClick = { onShowBottomSheet() },
                    onClick = {
                        // TODO:
                    }
                )
            )

            if (index < notifications.lastIndex) {
                HorizontalDivider(
                    thickness = 0.5.dp,
                    color = LocalExtendedColors.current.gray,
                    modifier = Modifier.padding(start = 60.dp)
                )
            }
        }
    }
}