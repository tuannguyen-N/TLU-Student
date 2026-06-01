package org.example.project.presentations.screen.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.model.ChatMessage
import org.example.project.domain.model.SseEvent
import org.example.project.domain.repository.ChatRepository

class ChatViewModel(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatState())
    val uiState = _uiState.asStateFlow()

    private fun updateState(newState: ChatState.() -> ChatState) {
        _uiState.update(newState)
    }

    init {
        loadChatContext()
    }

    private fun loadChatContext() {
        viewModelScope.launch {
            chatRepository.getChatbotContext().onSuccess {
                updateState { copy(chatbotContext = it) }
            }.onFailure {
                updateState { copy(error = it.message) }
            }
        }
    }

    fun onPromptChange(prompt: String) {
        updateState { copy(prompt = prompt) }
    }

    fun sendMessage() {
        val prompt = _uiState.value.prompt.trim()
        if (prompt.isEmpty()) return

        val userMessage = ChatMessage(text = prompt, isUser = true)
        updateState {
            copy(
                prompt = "",
                messages = messages + userMessage,
                isLoading = true,
                error = null
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            chatRepository.streamChat(prompt, _uiState.value.chatbotContext!!)
                .onStart { updateState { copy(isLoading = true) } }
                .onEach { event ->
                    when (event) {
                        is SseEvent.Token -> {
                            val decodedText = event.text
                                .replace("\\n", "\n")
                                .replace("\\r", "")
                                .replace("\\t", "\t")

                            val currentMessages = _uiState.value.messages
                            val lastMsg = currentMessages.lastOrNull()
                            val newMessages = if (lastMsg != null && !lastMsg.isUser) {
                                val updatedLast = ChatMessage(
                                    text = lastMsg.text + decodedText,
                                    isUser = false
                                )
                                currentMessages.dropLast(1) + updatedLast
                            } else {
                                currentMessages + ChatMessage(text = decodedText, isUser = false)
                            }
                            updateState { copy(messages = newMessages) }
                        }

                        is SseEvent.Error -> {
                            updateState { copy(error = event.message, isLoading = false) }
                        }

                        is SseEvent.Done -> {
                            updateState { copy(isLoading = false) }
                        }
                    }
                }
                .onCompletion { updateState { copy(isLoading = false) } }
                .catch { e ->
                    updateState { copy(error = e.message ?: "Unknown error", isLoading = false) }
                }
                .launchIn(this)
        }
    }

    fun clearError() {
        updateState { copy(error = null) }
    }
}