package org.example.project.data.mapper

import org.example.project.domain.model.ChatRoom
import org.example.project.domain.model.ConversationUiState
import org.example.project.domain.model.MessageType
import org.example.project.domain.model.User

fun ChatRoom.toConversationUiState(
    currentUserId: String,
    otherUser: User?
): ConversationUiState {

    return ConversationUiState(
        roomId = id,
        chatName = otherUser?.name ?: "Unknown",
        isOnline = otherUser?.isOnline ?: false,
        unreadCount = unreadCounts[currentUserId] ?: 0,
        lastMessageText = lastMessageText,
        lastMessageTimeFormatted = lastMessageTime.toString(),
        lastMessageType = MessageType.valueOf(lastMessageType),
        isLastMessageFromMe = lastSenderId == currentUserId,
        studentId = otherUser?.id ?: "Unknown"
    )
}