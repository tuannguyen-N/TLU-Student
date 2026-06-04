package org.example.project.presentations.screen.message.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.first
import org.example.project.domain.model.MessageStatus
import org.example.project.domain.model.MessageType
import org.example.project.domain.model.MessageUiState
import org.example.project.domain.utils.DateTimeUtils
import org.example.project.presentations.theme.LocalExtendedColors
import java.util.Calendar

@Composable
fun MessageContent(
    messages: List<MessageUiState>,
    isLoadingMore: Boolean,
    modifier: Modifier = Modifier,
    onClickFile: (String) -> Unit,
    onClickImage: (String) -> Unit,
    onLoadMoreMessage: () -> Unit
) {
    var visibleTimeId by remember { mutableStateOf<String?>(null) }
    val listState = rememberLazyListState()

    val messagesByDate = remember(messages) {
        messages.groupBy { message ->
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = message.timestamp
            val year = calendar.get(Calendar.YEAR)
            val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
            Pair(year, dayOfYear)
        }.toSortedMap(compareBy({ it.first }, { it.second }))
    }

    val totalItemCount = remember(messagesByDate) {
        messagesByDate.values.sumOf { it.size } + messagesByDate.size
    }

    var shouldAutoScroll by remember {
        mutableStateOf(true)
    }

    var initialScrollDone by remember { mutableStateOf(false) }

    LaunchedEffect(totalItemCount) {
        if (totalItemCount > 0 && !initialScrollDone) {
            snapshotFlow { listState.layoutInfo.totalItemsCount }
                .first { it >= totalItemCount }
            listState.scrollToItem(totalItemCount - 1)
            initialScrollDone = true
            return@LaunchedEffect
        }

        if (shouldAutoScroll && totalItemCount > 0) {
            listState.animateScrollToItem(totalItemCount - 1)
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow {
            listState.layoutInfo.visibleItemsInfo
        }.collect { visibleItems ->
            if (visibleItems.isNotEmpty()) {
                val lastVisibleIndex = visibleItems.lastOrNull()?.index ?: 0
                val totalItems = listState.layoutInfo.totalItemsCount
                shouldAutoScroll = lastVisibleIndex >= totalItems - 3
            }
        }
    }

    LaunchedEffect(totalItemCount) {
        if (shouldAutoScroll && totalItemCount > 0) {
            listState.animateScrollToItem(totalItemCount - 1)
        }
    }

    LaunchedEffect(messages.lastOrNull()?.id) {
        val lastMessage = messages.lastOrNull()
        if (lastMessage != null && !lastMessage.isMe) {
            listState.animateScrollToItem(totalItemCount - 1)
        }
    }

    var reachedTop by remember { mutableStateOf(false) }
    LaunchedEffect(listState) {
        snapshotFlow {
            listState.firstVisibleItemIndex == 0 &&
                    listState.firstVisibleItemScrollOffset == 0
        }.collect { isAtTop ->

            if (isAtTop && !reachedTop) {
                reachedTop = true
                Log.d("CHAT", "Đã kéo lên đầu danh sách")
                messages.lastOrNull()?.id?.let {
                    onLoadMoreMessage()
                }
            }

            if (!isAtTop) {
                reachedTop = false
            }
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .background(LocalExtendedColors.current.background)
            .padding(start = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        if (isLoadingMore) {
            item(
                key = "loading_more"
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                }
            }
        }

        messagesByDate.forEach { (date, messagesForDate) ->
            item {
                val firstMessage = messagesForDate.firstOrNull()
                if (firstMessage != null) {
                    DateDivider(timestamp = firstMessage.timestamp)
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            items(messagesForDate, key = { it.id }) { msg ->
                val isLast = msg == messagesForDate.lastOrNull()
                MessageBubble(
                    message = msg,
                    showTime = visibleTimeId == msg.id,
                    onClick = {
                        visibleTimeId =
                            if (visibleTimeId == msg.id) null
                            else msg.id
                    },
                    isLast = isLast,
                    onClickImage = { url ->
                        onClickImage(url)
                    },
                    onClickFile = onClickFile
                )
            }
        }
    }
}

@Composable
private fun DateDivider(timestamp: Long) {
    val label = DateTimeUtils.formatRelativeTime(timestamp)
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFFE0E0E0))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF8E8E93)),
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFFE0E0E0))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun MessageContentPreview() {
    val now = System.currentTimeMillis()

    val messages = listOf(
        MessageUiState(
            id = "1",
            senderId = "other",
            text = "Chào bạn! Bạn có thể giúp mình về bài tập không?",
            type = MessageType.TEXT.name,
            timestamp = now - 300_000,
            isMe = false,
            status = MessageStatus.SEEN
        ),
        MessageUiState(
            id = "2",
            senderId = "me",
            text = "Chào! Được chứ, bạn cần giúp môn gì vậy?",
            type = MessageType.TEXT.name,
            timestamp = now - 240_000,
            isMe = true,
            status = MessageStatus.SEEN
        ),
        MessageUiState(
            id = "3",
            senderId = "other",
            text = "Môn Toán rời rạc ạ, mình đang làm bài về đồ thị",
            type = MessageType.TEXT.name,
            timestamp = now - 180_000,
            isMe = false,
            status = MessageStatus.SEEN
        ),
        MessageUiState(
            id = "4",
            senderId = "me",
            text = "Oke mình hiểu rồi! Bạn đang gặp khó ở phần nào — DFS, BFS hay là tìm cây khung nhỏ nhất?",
            type = MessageType.TEXT.name,
            timestamp = now - 120_000,
            isMe = true,
            status = MessageStatus.SEEN
        ),
        // Ảnh không có text
        MessageUiState(
            id = "5",
            senderId = "other",
            fileUrl = "https://picsum.photos/seed/graph/400/300",
            type = MessageType.IMAGE.name,
            timestamp = now - 90_000,
            isMe = false,
            status = MessageStatus.SEEN
        ),
        // Ảnh kèm text
        MessageUiState(
            id = "6",
            senderId = "me",
            fileUrl = "https://picsum.photos/seed/dfs/400/300",
            text = "Đây là sơ đồ DFS mình tự vẽ, bạn xem thử nhé!",
            type = MessageType.IMAGE.name,
            timestamp = now - 80_000,
            isMe = true,
            status = MessageStatus.SEEN
        ),
        MessageUiState(
            id = "7",
            senderId = "me",
            fileName = "graph_theory_notes.pdf",
            fileSize = "1.2 MB",
            type = MessageType.FILE.name,
            timestamp = now - 60_000,
            isMe = true,
            status = MessageStatus.SENT
        ),
        MessageUiState(
            id = "8",
            senderId = "me",
            text = "Tin nhắn này đang gửi...",
            type = MessageType.TEXT.name,
            timestamp = now - 10_000,
            isMe = true,
            status = MessageStatus.SENDING
        ),
        MessageUiState(
            id = "9",
            senderId = "me",
            text = "Tin nhắn gửi thất bại, thử lại sau nhé!",
            type = MessageType.TEXT.name,
            timestamp = now,
            isMe = true,
            status = MessageStatus.SEEN
        )
    )

//    MessageContent(messages = messages, onClickFile = {}, onClickImage = {})
}