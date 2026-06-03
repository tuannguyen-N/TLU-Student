package org.example.project.presentations.screen.messages.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.ConversationUiState
import org.example.project.domain.model.MessageType
import org.example.project.domain.model.User

private val sampleConversations = listOf(
    ConversationUiState(
        roomId = "1", chatName = "Nguyễn Văn A", isOnline = true, unreadCount = 2,
        lastMessageText = "Tớ vừa gửi tài liệu xong, cậu kiểm tra nhé!",
        lastMessageTimeFormatted = "10:22 AM",
        lastMessageType = MessageType.TEXT,
        isLastMessageFromMe = false,
        studentId = "1"
    ),
    ConversationUiState(
        roomId = "2", chatName = "Lê Thị B", isOnline = false, unreadCount = 0,
        lastMessageText = "Chiều nay họp nhóm nhé mọi người!",
        lastMessageTimeFormatted = "09:45 AM",
        lastMessageType = MessageType.TEXT,
        isLastMessageFromMe = false,
        studentId = "2"
    ),
    ConversationUiState(
        roomId = "3", chatName = "Nhóm Đồ án Cuối kỳ", isOnline = false, unreadCount = 0,
        lastMessageText = "Trần C: Mọi người nhớ nộp bài đúng hạn...",
        lastMessageTimeFormatted = "Yesterday",
        lastMessageType = MessageType.TEXT,
        isLastMessageFromMe = false,
        studentId = "3"
    ),
    ConversationUiState(
        roomId = "4", chatName = "Phạm Minh", isOnline = false, unreadCount = 0,
        lastMessageText = "Cảm ơn cậu nhiều nhé!",
        lastMessageTimeFormatted = "Sunday",
        lastMessageType = MessageType.TEXT,
        isLastMessageFromMe = false,
        studentId = "4"
    ),
    ConversationUiState(
        roomId = "5", chatName = "Hoàng Lan", isOnline = false, unreadCount = 1,
        lastMessageText = "Bài tập tuần này khó quá, giúp tớ với!",
        lastMessageTimeFormatted = "Sat",
        lastMessageType = MessageType.TEXT,
        isLastMessageFromMe = false,
        studentId = "5"
    )
)

@Composable
fun MessagesContent(
    modifier: Modifier = Modifier,
    conversations: List<ConversationUiState> = sampleConversations,
    users: List<User>,
    onOpenMessage: (id: String, chatUserId: String, chatUserName: String) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            ActiveUsersSection(users = users)
        }
        item {
            Text(
                text = "Gần đây",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
        items(conversations, key = { it.roomId }) { conversation ->
            ConversationItem(conversation = conversation, onOpenMessage = {
                onOpenMessage(conversation.roomId, conversation.studentId, conversation.chatName)
            })
            HorizontalDivider(
                modifier = Modifier.padding(start = 80.dp),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }
    }
}

@Composable
fun ActiveUsersSection(users: List<User>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Đang hoạt động",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )
        }
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(users) { user ->
                ActiveUserItem(
                    name = user.name.substringAfterLast(' '),
                    avatarUrl = user.avatarUrl
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun ActiveUserItem(name: String, avatarUrl: String?) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box {
            AvatarImage(avatarUrl = avatarUrl, name = name, size = 56)
            Box(
                modifier = Modifier
                    .size(14.dp)
                    .align(Alignment.BottomEnd)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface) // border effect
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF4CAF50))
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.labelMedium
        )
    }
}