package org.example.project.domain.repository

import io.ktor.client.HttpClient
import io.ktor.client.request.preparePost
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.utils.io.readUTF8Line
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.example.project.data.remote.api.ChatApi
import org.example.project.data.remote.dto.chatbot.ChatMessageContext
import org.example.project.data.remote.dto.chatbot.ChatRequest
import org.example.project.data.remote.dto.chatbot.ChatbotContextData
import org.example.project.domain.model.AppResult
import org.example.project.domain.model.SseEvent

class ChatRepository(
    private val httpClient: HttpClient,
    private val chatApi: ChatApi
) {

    private val json = Json { ignoreUnknownKeys = true }

    private val _chatbotContext = MutableStateFlow<ChatbotContextData?>(null)

    suspend fun refreshChatbotContext(): AppResult<ChatbotContextData> {
        return try {
            val data = chatApi.getChatbotContext().data
            if (data != null) {
                _chatbotContext.value = data
                AppResult.Success(data)
            } else {
                AppResult.Failure(null)
            }
        } catch (e: Exception) {
            AppResult.Failure(e.message, e)
        }
    }

    fun streamChat(
        prompt: String,
        messages: List<ChatMessageContext>
    ): Flow<SseEvent> = flow {
        val context = _chatbotContext.value

        val request = ChatRequest(
            prompt = prompt,
            context = context,
            messages = messages
        )

        httpClient.preparePost(
            "https://tl-chatbot.nhokthanh3211.workers.dev/api/v1/agent-chat-stream"
        ) {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.execute { response ->
            val channel = response.bodyAsChannel()

            try {
                while (!channel.isClosedForRead) {
                    val line = channel.readUTF8Line() ?: break
                    if (line.isBlank()) continue
                    if (!line.startsWith("data:")) continue
                    val data = line.removePrefix("data:").trim()

                    if (data == "[DONE]") {
                        emit(SseEvent.Done)
                        break
                    }

                    val text = try {
                        json.parseToJsonElement(data)
                            .jsonObject["text"]
                            ?.jsonPrimitive
                            ?.content
                    } catch (e: Exception) {
                        null
                    } ?: continue

                    val cleanText = text
                        .replace("[e-n-t-e-r]", "\n")
                        .replace("\\n", "\n")
                    if (cleanText == "Đang xử lý yêu cầu...") continue
                    emit(SseEvent.Token(cleanText))
                }
            } catch (e: Exception) {
                emit(SseEvent.Error(e.message ?: "Stream error"))
            }
        }
    }
}