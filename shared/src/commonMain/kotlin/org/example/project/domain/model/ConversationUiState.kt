package org.example.project.domain.model

data class ConversationUiState(
    val roomId: String,
    val studentId: String,
    val chatName: String,
    val avatarUrl: String? = null,
    val isOnline: Boolean = false,
    val unreadCount: Int = 0,
    val lastMessageText: String,
    val lastMessageTimeFormatted: String,
    val lastMessageType: MessageType,
    val isLastMessageFromMe: Boolean
)