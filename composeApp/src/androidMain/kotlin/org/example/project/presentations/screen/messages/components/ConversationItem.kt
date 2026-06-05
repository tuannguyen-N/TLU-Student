package org.example.project.presentations.screen.messages.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.ConversationUiState
import org.example.project.domain.model.MessageType
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun ConversationItem(
    conversation: ConversationUiState,
    onOpenMessage: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .clickable(onClick = onOpenMessage),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AvatarWithPresence(
            avatarUrl = conversation.avatarUrl,
            name = conversation.chatName.substringAfterLast(" "),
            isOnline = conversation.isOnline,
            lastSeen = conversation.lastSeen,
            size = 56
        )

        Spacer(modifier = Modifier.width(12.dp))

        MessageContent(
            name = conversation.chatName,
            lastMessageText = conversation.lastMessageText,
            lastMessageTimeFormatted = conversation.lastMessageTimeFormatted,
            lastMessageType = conversation.lastMessageType,
            isLastMessageFromMe = conversation.isLastMessageFromMe,
            unreadCount = conversation.unreadCount,
            modifier = Modifier.weight(1f),
            studentId = conversation.studentId
        )
    }
}

@Composable
fun MessageContent(
    studentId: String,
    name: String,
    lastMessageText: String,
    lastMessageTimeFormatted: String,
    lastMessageType: MessageType,
    isLastMessageFromMe: Boolean,
    unreadCount: Int,
    modifier: Modifier = Modifier
) {
    val hasUnread = unreadCount > 0
    val prefix = if (isLastMessageFromMe) "Bạn: " else ""

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "$studentId - $name",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(2.dp))

            when (lastMessageType) {
                MessageType.TEXT -> Text(
                    text = prefix + lastMessageText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (hasUnread)
                        MaterialTheme.colorScheme.onSurface
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = if (hasUnread) FontWeight.Medium else FontWeight.Normal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                MessageType.FILE -> Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = prefix,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Icon(
                        imageVector = Icons.Default.AttachFile,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = lastMessageText,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                MessageType.IMAGE -> Text(
                    text = prefix + "[Hình ảnh]",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (hasUnread)
                        MaterialTheme.colorScheme.onSurface
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = if (hasUnread) FontWeight.Medium else FontWeight.Normal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = lastMessageTimeFormatted,
                style = MaterialTheme.typography.labelSmall,
                color = if (hasUnread)
                    LocalExtendedColors.current.midBlue
                else
                    MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (hasUnread) {
                Spacer(modifier = Modifier.height(4.dp))
                UnreadBadge(count = unreadCount)
            }
        }
    }
}

@Composable
private fun UnreadBadge(count: Int) {
    Box(
        modifier = Modifier
            .defaultMinSize(minWidth = 20.dp, minHeight = 20.dp)
            .clip(RoundedCornerShape(50))
            .background(LocalExtendedColors.current.red),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (count > 99) "99+" else count.toString(),
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = Color.White,
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
        )
    }
}