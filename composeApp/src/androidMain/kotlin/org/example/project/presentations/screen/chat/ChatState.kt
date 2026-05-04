package org.example.project.presentations.screen.chat

import org.example.project.domain.model.ChatMessage

data class ChatState(
    val prompt: String = "",
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
