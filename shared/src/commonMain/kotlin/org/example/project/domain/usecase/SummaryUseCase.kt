package org.example.project.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.project.domain.model.SenderType
import org.example.project.domain.model.SseEvent
import org.example.project.domain.repository.MessageRepository
import org.example.project.domain.repository.SummaryRepository

class SummaryUseCase(
    private val summaryRepository: SummaryRepository,
    private val messageRepository: MessageRepository
) {
    fun summarize(
        roomId: String,
        messageId: String,
    ): Flow<SseEvent> = flow {
        var accumulatedText = ""

        summaryRepository.summarizeStream(roomId, messageId)
            .collect { event ->
                when (event) {
                    is SseEvent.Token -> {
                        accumulatedText += event.text
                        emit(event)
                    }
                    is SseEvent.Error -> {
                        accumulatedText = event.message
                        emit(event)
                    }
                    is SseEvent.Done -> {
                        messageRepository.sendMessage(
                            roomId = roomId,
                            currentUserId = "tlu_ai",
                            senderType = SenderType.AI,
                            message = accumulatedText
                        )
                        emit(SseEvent.Done)
                    }
                }
            }
    }
}