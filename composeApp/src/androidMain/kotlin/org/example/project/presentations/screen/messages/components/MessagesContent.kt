package org.example.project.presentations.screen.messages.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import org.example.project.domain.model.UserUiModel

@Composable
fun MessagesContent(
    modifier: Modifier = Modifier,
    conversations: List<ConversationUiState>,
    users: List<UserUiModel>,
    onOpenMessage: (chatUserId: String, chatUserName: String) -> Unit,
    onStartNewChat: () -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            ActiveUsersSection(
                users = users,
                onOpenMessage = { studentId, studentName ->
                    onOpenMessage(studentId, studentName)
                }
            )
        }

        if (conversations.isEmpty()) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                EmptyMessagesCard(
                    onStartChatClick = onStartNewChat
                )
            }
        } else {
            item {
                Text(
                    text = "Gần đây",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            items(conversations, key = { it.roomId }) { conversation ->
                ConversationItem(
                    conversation = conversation,
                    onOpenMessage = {
                        onOpenMessage(conversation.studentId, conversation.chatName)
                    }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(start = 80.dp),
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }
        }
    }
}

@Composable
fun ActiveUsersSection(
    users: List<UserUiModel>,
    onOpenMessage: (String, String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(users) { user ->
                ActiveUserItem(
                    name = user.name.substringAfterLast(" "),
                    avatarUrl = user.avatarUrl,
                    isOnline = user.isOnline,
                    onClick = {
                        onOpenMessage(user.studentCode, user.name)
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun ActiveUserItem(
    name: String,
    avatarUrl: String?,
    isOnline: Boolean,
    onClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier.clickable(onClick = onClick)
        ) {
            AvatarImage(avatarUrl = avatarUrl, name = name, size = 56)
            if (isOnline) {
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
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.labelMedium
        )
    }
}