package org.example.project.presentations.screen.chat

import org.example.project.data.remote.dto.chatbot.ChatMessageContext
import org.example.project.data.remote.dto.chatbot.ChatbotContextData
import org.example.project.domain.model.ChatMessage

data class ChatState(
    val prompt: String = "",
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,

    val messageContext: List<ChatMessageContext> = emptyList(),
    val chatbotContext: ChatbotContextData? = null
)