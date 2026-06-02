package org.example.project.domain.model

enum class ChatRoomType { DIRECT, GROUP }

data class ChatRoom(
    val id: String = "",
    val type: String = ChatRoomType.DIRECT.name,
    val participantIds: List<String> = emptyList(),
    val unreadCounts: Map<String, Int> = emptyMap(),
    val lastReadAt: Map<String, Long> = emptyMap(),
    val lastMessageText: String = "",
    val lastMessageTime: Long = 0L,
    val lastSenderId: String = "",
    val lastMessageType: String = MessageType.TEXT.name
)