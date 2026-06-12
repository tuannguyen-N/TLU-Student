package org.example.project.domain.repository

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.example.project.data.remote.api.SummaryApi
import org.example.project.domain.model.SseEvent

class SummaryRepository(private val summaryApi: SummaryApi) {

    fun summarizeStream(
        roomId: String,
        messageId: String
    ): Flow<SseEvent> = callbackFlow {
        try {
            summaryApi.summarizeStream(
                roomId = roomId,
                messageId = messageId,
                onChunk = { trySend(SseEvent.Token(it)) },
                onDone = {
                    trySend(SseEvent.Done)
                    close()
                },
                onError = {
                    trySend(SseEvent.Error(it))
                    close()
                }
            )
        } catch (e: Exception) {
            trySend(SseEvent.Error("Server đang bận"))
            close()
        }

        awaitClose()
    }
}