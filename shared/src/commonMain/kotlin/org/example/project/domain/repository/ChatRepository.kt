package org.example.project.domain.repository

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.preparePost
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.readUTF8Line
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.example.project.domain.model.SseEvent

class ChatRepository(
    private val httpClient: HttpClient
) {
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

                    val data = line.removePrefix("data:").let {
                        if (it.startsWith(" ")) it.substring(1) else it
                    }
                    if (data == "[DONE]") {
                        emit(SseEvent.Done)
                        break
                    }

                    val cleanData = data
                        .replace("[e-n-t-e-r]", "\n")
                        .replace("\\n", "\n")
                    emit(SseEvent.Token(cleanData))
                }
            } catch (e: Exception) {
                emit(SseEvent.Error(e.message ?: "Stream error"))
            }
        }
    }
}