package org.example.project.presentations.screen.notification.components

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import org.example.project.domain.model.NotificationUiModel
import org.example.project.presentations.theme.ExtendedColors
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun NotificationList(
    modifier: Modifier = Modifier,
    color: ExtendedColors,
    onShowBottomSheet: () -> Unit,
    notifications: List<NotificationUiModel>,
    selectedTab: Int,
    isLoadingMore: Boolean,
    hasMore: Boolean,
    onLoadMore: () -> Unit,
    onClickNotification: (NotificationUiModel) -> Unit
) {
    val listState = rememberLazyListState()
    LaunchedEffect(selectedTab) {
        listState.scrollToItem(0)
    }

    val previousSize = remember { mutableIntStateOf(notifications.size) }

    LaunchedEffect(notifications.size) {
        val newSize = notifications.size
        val oldSize = previousSize.intValue

        if (newSize > oldSize && listState.firstVisibleItemIndex <= 1) {
            listState.animateScrollToItem(0)
        }
        previousSize.intValue = newSize
    }

    LaunchedEffect(listState) {
        snapshotFlow {
            val layoutInfo = listState.layoutInfo
            val lastVisible = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val total = layoutInfo.totalItemsCount
            lastVisible >= total - 3
        }.distinctUntilChanged().filter { it }.collect { onLoadMore() }
    }

    LazyColumn(
        modifier = modifier, state = listState
    ) {
        if (notifications.isEmpty()) {
            item {
                val senderLabel = when (selectedTab) {
                    1 -> "Hệ thống"
                    2 -> "Giảng viên"
                    3 -> "Khoa"
                    else -> null
                }
                Box(
                    modifier = Modifier
                        .fillParentMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (senderLabel != null) "Không có thông báo từ $senderLabel"
                        else "Không có thông báo nào",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = color.gray
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {

            items(items = notifications, key = { it.id }) { item ->
                NotificationItem(
                    notification = item,
                    color = color,
                    modifier = Modifier
                        .animateItem()
                        .combinedClickable(
                            onLongClick = { onShowBottomSheet() },
                            onClick = { onClickNotification(item) })
                )
                HorizontalDivider(
                    thickness = 0.2.dp,
                    color = LocalExtendedColors.current.gray,
                    modifier = Modifier.padding(start = 60.dp)
                )
            }

            item {
                when {
                    isLoadingMore -> Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    }
                }
            }
        }
    }
}