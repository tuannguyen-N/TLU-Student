package org.example.project.domain.model

enum class MessageType {
    TEXT,
    FILE,
    IMAGE
}

data class Message(
    val id: String = "",
    val senderId: String = "",
    val text: String? = null,
    val fileUrl: String? = null,
    val fileName: String? = null,
    val fileSize: String? = null,
    val type: String = MessageType.TEXT.name,
    val timestamp: Long = 0L,
    val senderType: String = "USER"
)