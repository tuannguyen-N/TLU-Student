package org.example.project.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.request.preparePost
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.utils.io.readUTF8Line
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.jsonPrimitive

class SummaryApi(private val client: HttpClient) {

    suspend fun summarizeStream(
        roomId: String,
        messageId: String,
        onChunk: (String) -> Unit,
        onDone: (fileName: String) -> Unit,
        onError: (String) -> Unit
    ) {
        client.preparePost("https://us-central1-tlu-student-chatting.cloudfunctions.net/summarizeDocument") {
            contentType(ContentType.Application.Json)
            setBody(mapOf("roomId" to roomId, "messageId" to messageId))
        }.execute { response ->
            val channel = response.bodyAsChannel()
            while (!channel.isClosedForRead) {
                val line = channel.readUTF8Line() ?: continue
                if (!line.startsWith("data: ")) continue

                val json = line.removePrefix("data: ")
                val map = Json.decodeFromString<Map<String, JsonElement>>(json)

                when {
                    map.containsKey("error") -> {
                        onError(map["error"]!!.jsonPrimitive.content)
                        break
                    }

                    map["done"]?.jsonPrimitive?.booleanOrNull == true -> {
                        val fileName = map["fileName"]?.jsonPrimitive?.content ?: ""
                        onDone(fileName)
                        break
                    }

                    map.containsKey("text") -> {
                        onChunk(map["text"]!!.jsonPrimitive.content)
                    }
                }
            }
        }
    }
}