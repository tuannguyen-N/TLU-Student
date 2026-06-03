package org.example.project.data.mapper

import org.example.project.domain.model.ChatRoom
import org.example.project.domain.model.ConversationUiState
import org.example.project.domain.model.Message
import org.example.project.domain.model.MessageStatus
import org.example.project.domain.model.MessageType
import org.example.project.domain.model.MessageUiState
import org.example.project.domain.model.User
import org.example.project.domain.utils.DateTimeUtils

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
        lastMessageTimeFormatted = DateTimeUtils.formatRelativeTime(lastMessageTime),
        lastMessageType = MessageType.valueOf(lastMessageType),
        isLastMessageFromMe = lastSenderId == currentUserId,
        studentId = otherUser?.id ?: "Unknown"
    )
}

fun Message.toUiState(
    currentUserId: String
): MessageUiState {
    return MessageUiState(
        id = id,
        senderId = senderId,

        text = text,

        fileUrl = fileUrl,
        fileName = fileName,
        fileSize = fileSize,

        type = type,
        timestamp = timestamp,

        isMe = senderId.equals(
            currentUserId,
            ignoreCase = true
        ),
        status = MessageStatus.SENT
    )
}