package org.example.project.domain.model


sealed class SseEvent {
    data class Token(val text: String) : SseEvent()
    data class SessionReceived(val sessionId: String) : SseEvent()
    data class Error(val message: String) : SseEvent()
    object Done : SseEvent()
}