package org.example.project.data.remote.dto.chatbot

import kotlinx.serialization.Serializable

@Serializable
data class ChatRequest(
    val prompt: String,
    val messages: List<ChatMessageContext> = emptyList(),
    val context: ChatbotContextData?
)

@Serializable
data class ChatMessageContext(
    val role: String,
    val content: String
)