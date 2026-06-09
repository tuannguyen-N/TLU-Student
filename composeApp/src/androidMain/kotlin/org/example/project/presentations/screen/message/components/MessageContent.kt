package org.example.project.presentations.screen.message.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import org.example.project.domain.model.MessageUiState
import org.example.project.domain.model.UserUiModel
import org.example.project.domain.utils.DateTimeUtils
import org.example.project.presentations.theme.LocalExtendedColors
import java.util.Calendar

@Composable
fun MessageContent(
    messages: List<MessageUiState>,
    hasMoreMessages: Boolean,
    isLoadingMore: Boolean,
    chatUser: UserUiModel?,
    modifier: Modifier = Modifier,
    onClickFile: (String) -> Unit,
    onClickImage: (String) -> Unit,
    onLoadMoreMessage: () -> Unit,
    isAiReplying: Boolean
) {
    var visibleTimeId by remember { mutableStateOf<String?>(null) }
    val listState = rememberLazyListState()

    // ── Group by date ────────────────────────────────────────────────────────
    val messagesByDate = remember(messages) {
        messages
            .groupBy { msg ->
                val cal = Calendar.getInstance().apply { timeInMillis = msg.timestamp }
                cal.get(Calendar.YEAR) to cal.get(Calendar.DAY_OF_YEAR)
            }
            .toSortedMap(compareBy({ it.first }, { it.second }))
    }

    LaunchedEffect(isLoadingMore) {
        Log.d("CHAT_LOADING", "isLoadingMore = $isLoadingMore")
    }

    val lastMessageId = messages.lastOrNull()?.id
    LaunchedEffect(lastMessageId) {
        if (lastMessageId == null) return@LaunchedEffect
        val firstVisible = listState.layoutInfo.visibleItemsInfo.firstOrNull()?.index ?: 0
        val isNearBottom = firstVisible <= 2
        if (isNearBottom) {
            listState.animateScrollToItem(0)
        }
    }

    // ── Load more when scrolled to top ───────────────────────────────────────
    LaunchedEffect(listState, hasMoreMessages, isLoadingMore) {
        snapshotFlow {
            val layoutInfo = listState.layoutInfo

            val lastVisibleIndex =
                layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

            lastVisibleIndex >= layoutInfo.totalItemsCount - 1
        }
            .distinctUntilChanged()
            .filter { it }
            .collect {
                if (hasMoreMessages && !isLoadingMore) {
                    onLoadMoreMessage()
                }
            }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            state = listState,
            reverseLayout = true,
            modifier = Modifier
                .fillMaxSize()
                .background(LocalExtendedColors.current.background)
                .padding(start = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            messagesByDate
                .entries.sortedByDescending { it.key.first * 1000 + it.key.second }
                .forEach { (_, messagesForDate) ->
                    items(messagesForDate.reversed(), key = { it.id }) { msg ->
                        MessageBubble(
                            message = msg,
                            showTime = visibleTimeId == msg.id,
                            onClick = {
                                visibleTimeId = if (visibleTimeId == msg.id) null else msg.id
                            },
                            isLast = msg == messagesForDate.last(),
                            onClickImage = onClickImage,
                            onClickFile = onClickFile,
                            avatarUrl = chatUser?.avatarUrl,
                            chatUserName = chatUser?.name ?: "",
                            isAiReplying = isAiReplying
                        )
                    }

                    item(key = "header_${messagesForDate.first().timestamp}") {
                        DateDivider(timestamp = messagesForDate.first().timestamp)
                    }
                }
        }
        AnimatedVisibility(
            visible = isLoadingMore,
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Box(
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp
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