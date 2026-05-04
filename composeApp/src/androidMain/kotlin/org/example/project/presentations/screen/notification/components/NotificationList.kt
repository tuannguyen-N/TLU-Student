package org.example.project.presentations.screen.notification.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.NotificationUiModel
import org.example.project.presentations.theme.ExtendedColors
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun NotificationList(
    modifier: Modifier = Modifier,
    color: ExtendedColors,
    onShowBottomSheet: () -> Unit,
    notifications: List<NotificationUiModel>,
    newNotificationIds: Set<Int>,
    selectedTab: Int,
    onClickNotification: (NotificationUiModel) -> Unit
) {
    val listState = rememberLazyListState()

    LaunchedEffect(selectedTab) {
        listState.scrollToItem(0)
    }

    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(
            items = notifications,
            key = { it.id }
        ) { item ->
            val isNew = item.id in newNotificationIds

            NotificationItem(
                notification = item,
                color = color,
                modifier = Modifier
                    .animateItem(
                        fadeInSpec = if (isNew) tween(300) else null,
                        placementSpec = null
                    )
                    .combinedClickable(
                        onLongClick = { onShowBottomSheet() },
                        onClick = {
                            onClickNotification(item)
                        }
                    )
            )

            HorizontalDivider(
                thickness = 0.2.dp,
                color = LocalExtendedColors.current.gray,
                modifier = Modifier.padding(start = 60.dp)
            )
        }
    }
}