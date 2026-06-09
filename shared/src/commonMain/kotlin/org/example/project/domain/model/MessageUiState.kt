package org.example.project.domain.model

data class MessageUiState(
    val id: String,
    val senderId: String,
    val text: String? = null,
    val fileUrl: String? = null,
    val fileName: String? = null,
    val fileSize: String? = null,
    val type: String,
    val timestamp: Long,
    val isMe: Boolean,
    val status: MessageStatus = MessageStatus.SENDING,
    val senderType: SenderType = SenderType.USER
)

enum class SenderType {
    USER,
    AI
}