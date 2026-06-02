package org.example.project.presentations.screen.message.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.Message
import org.example.project.domain.model.MessageType
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun MessageContent(
    modifier: Modifier = Modifier
) {
    var visibleTimeId by remember { mutableStateOf<String?>(null) }

    val listState = rememberLazyListState()

    LaunchedEffect(sampleMessages.size) {
        if (sampleMessages.isNotEmpty()) {
            listState.animateScrollToItem(sampleMessages.lastIndex)
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .background(LocalExtendedColors.current.background)
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        item {
            DateDivider(label = "Hôm nay")
            Spacer(modifier = Modifier.height(4.dp))
        }

        items(sampleMessages, key = { it.id }) { msg ->
            MessageBubble(
                message = msg,
                showTime = visibleTimeId == msg.id,
                onClick = {
                    visibleTimeId =
                        if (visibleTimeId == msg.id) null
                        else msg.id
                }
            )
        }
    }
}

@Composable
private fun DateDivider(label: String) {
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


private val sampleMessages = listOf(
    Message(
        id = "1", senderId = "other_user",
        text = "Chào cậu, cậu đã làm xong bài tập nhóm môn Kinh tế vĩ mô chưa?",
        timestamp = 1780312500000L
    ),
    Message(
        id = "2", senderId = "current_user",
        text = "Tớ vừa hoàn thành xong phần biểu đồ rồi đây. Để tớ gửi file cho cậu xem thử nhé! \uD83D\uDCCA",
        timestamp = 1780312620000L
    ),
    Message(
        id = "3", senderId = "current_user",
        type = MessageType.FILE.name,
        fileName = "Baitap_Nhom_Vimo.pdf",
        fileSize = "2.4 MB",
        timestamp = 1780312680000L
    ),
    Message(
        id = "4", senderId = "other_user",
        text = "Tuyệt vời! Để tớ kiểm tra lại rồi tổng hợp vào slide luôn. Cảm ơn cậu nhé.",
        timestamp = 1780312800000L
    ),
    Message(
        id = "5", senderId = "other_user",
        text = "Chiều nay 2h mình họp nhóm ở thư viện tầng 3 được không?",
        timestamp = 1780312800000L
    ),
    Message(
        id = "6", senderId = "other_user",
        text = "Tuyệt vời! Để tớ kiểm tra lại rồi tổng hợp vào slide luôn. Cảm ơn cậu nhé.",
        timestamp = 1780312800000L
    ),
    Message(
        id = "7", senderId = "other_user",
        text = "Chiều nay 2h mình họp nhóm ở thư viện tầng 3 được không?",
        timestamp = 1780312800000L
    ),
    Message(
        id = "8", senderId = "other_user",
        text = "Tuyệt vời! Để tớ kiểm tra lại rồi tổng hợp vào slide luôn. Cảm ơn cậu nhé.",
        timestamp = 1780312800000L
    ),
    Message(
        id = "9", senderId = "other_user",
        text = "Chiều nay 2h mình họp nhóm ở thư viện tầng 3 được không?",
        timestamp = 1780312800000L
    ),
    Message(
        id = "10", senderId = "other_user",
        text = "Tuyệt vời! Để tớ kiểm tra lại rồi tổng hợp vào slide luôn. Cảm ơn cậu nhé.",
        timestamp = 1780312800000L
    ),
    Message(
        id = "11", senderId = "other_user",
        text = "Chiều nay 2h mình họp nhóm ở thư viện tầng 3 được không?",
        timestamp = 1780312800000L
    ),
    Message(
        id = "12", senderId = "current_user",
        text = "Okie chốt nhé! Tớ sẽ đến đúng giờ. \uD83D\uDC4D",
        timestamp = 1780312920000L
    )
)

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun MessageScreenPreview() {
    MaterialTheme {
        MessageContent()
    }
}