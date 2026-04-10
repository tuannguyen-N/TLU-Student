package org.example.project.domain.repository

import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.preparePost
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.readUTF8Line
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.example.project.domain.model.SseEvent

class ChatRepository(
    private val httpClient: HttpClient
) {
    fun streamChat(
        chatbotId: String,
        prompt: String,
        sessionId: String? = null
    ): Flow<SseEvent> = flow {
        httpClient.preparePost("https://your-api.com/api/v1/chatbot/$chatbotId") {
            contentType(ContentType.Application.Json)
            sessionId?.let { header("X-Session-Id", it) }
            setBody("""{"prompt": "$prompt"}""")
        }.execute { response ->
            val channel: ByteReadChannel = response.bodyAsChannel()

            while (!channel.isClosedForRead) {
                val line = channel.readUTF8Line() ?: break
                if (!line.startsWith("data:")) continue

                val data = line.removePrefix("data:").trim()
                if (data == "[DONE]") {
                    emit(SseEvent.Done)
                    break
                }

                try {
                    val json = Json.parseToJsonElement(data).jsonObject
                    val sessionIdValue = json["session_id"]?.jsonPrimitive?.content
                    val text = json["text"]?.jsonPrimitive?.content

                    if (sessionIdValue != null) emit(SseEvent.SessionReceived(sessionIdValue))
                    if (text != null) emit(SseEvent.Token(text))
                } catch (e: Exception) {
                    emit(SseEvent.Error(e.message ?: "Parse error"))
                }
            }
        }
    }
}