package org.example.project.domain.repository

import io.ktor.client.HttpClient
import io.ktor.client.request.preparePost
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.readUTF8Line
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.project.domain.model.SseEvent

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class ChatRepository(
    private val httpClient: HttpClient
) {
    private val json = Json { ignoreUnknownKeys = true }

    fun streamChat(prompt: String): Flow<SseEvent> = flow {
        httpClient.preparePost("https://tl-chatbot.nhokthanh3211.workers.dev/api/v1/agent-chat-stream") {
            contentType(ContentType.Application.Json)
            setBody("""{"prompt": "$prompt"}""")
        }.execute { response ->
            val channel: ByteReadChannel = response.bodyAsChannel()
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