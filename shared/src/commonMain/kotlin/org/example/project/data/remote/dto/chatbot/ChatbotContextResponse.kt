package org.example.project.data.remote.dto.chatbot

import kotlinx.serialization.Serializable

@Serializable
data class ChatbotContextResponse(
    val code: Int,
    val data: ChatbotContextData?,
    val message: String
)