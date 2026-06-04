package org.example.project.presentations.screen.message

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.model.MessageStatus
import org.example.project.domain.model.MessageType
import org.example.project.domain.model.MessageUiState
import org.example.project.domain.model.User
import org.example.project.domain.repository.MessageRepository
import org.example.project.domain.usecase.StudentUseCase
import java.util.UUID

class MessageViewModel(
    savedStateHandle: SavedStateHandle,
    private val messageRepository: MessageRepository,
    private val studentUseCase: StudentUseCase
) : ViewModel() {
    private val chatUserId = savedStateHandle.get<String>("studentId") ?: ""
    private val chatUserName = savedStateHandle.get<String>("chatName") ?: ""
    val studentId = studentUseCase.studentInfo.value?.studentCode?.lowercase()
    val roomId = generateRoomId(
        studentId!!.lowercase(),
        chatUserId.lowercase()
    )

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore = _isLoadingMore.asStateFlow()

    private val pendingMessages = MutableStateFlow<List<MessageUiState>>(emptyList())

    private val _chatUser = MutableStateFlow(
        User(
            id = chatUserId,
            name = chatUserName,
            avatarUrl = "",
            isOnline = false
        )
    )

    val chatUser = _chatUser.asStateFlow()

    private val messageLimit = MutableStateFlow(30L)

    private val remoteMessages =
        messageLimit.flatMapLatest { limit ->
            messageRepository.observeMessages(
                roomId = roomId,
                currentUserId = studentId!!,
                limit = limit
            )
        }.shareIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            replay = 1
        )

    val messages = combine(
        remoteMessages,
        pendingMessages
    ) { remote, pending ->
        val latestRemoteTime =
            remote.maxOfOrNull { it.timestamp } ?: 0L

        val stillPending =
            pending.filter {
                it.timestamp > latestRemoteTime
            }

        (remote + stillPending)
            .distinctBy { it.id }
            .sortedBy { it.timestamp }
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private val _uiState = MutableStateFlow(MessageState())
    val uiState = _uiState.asStateFlow()

    init {
        observeMessagesLoaded()

        markAsRead()
        observeUserOnlineStatus()
        observeAndMarkNewMessages()

        loadChatStudent()
    }

    private fun loadChatStudent() {
        viewModelScope.launch {
            studentUseCase.getStudentInfo(chatUserId).onSuccess { student ->
                updateState { copy(chatStudent = student) }
            }.onFailure {
                Log.e("123123", "loadChatStudent: $it")
            }
        }
    }

    private fun observeMessagesLoaded() {
        viewModelScope.launch {
            remoteMessages.first()

            updateState {
                copy(isLoading = false)
            }
        }
    }

    private fun observeAndMarkNewMessages() {
        viewModelScope.launch {
            remoteMessages
                .distinctUntilChangedBy { messages ->
                    messages.maxOfOrNull { it.timestamp } ?: 0L
                }
                .drop(1)
                .filter { messages ->
                    messages.lastOrNull()?.isMe == false
                }
                .collect {
                    messageRepository.markConversationAsRead(roomId, studentId!!)
                }
        }
    }

    private fun observeUserOnlineStatus() {
        viewModelScope.launch {
            messageRepository.observeUserOnlineStatus(chatUserId)
                .collect { isOnline ->
                    Log.d("ONLINE_STATUS", "Firestore = $isOnline")

                    _chatUser.update {
                        Log.d("ONLINE_STATUS", "Before = ${it.isOnline}")
                        it.copy(isOnline = isOnline)
                    }

                    Log.d("ONLINE_STATUS", "After = ${_chatUser.value.isOnline}")
                }
        }
    }

    fun loadMoreMessages() {
        if (_isLoadingMore.value) return
        viewModelScope.launch {
            _isLoadingMore.value = true
            try {
                val currentCount = messages.value.size
                if (currentCount < 30) {
                    _isLoadingMore.value = false
                    return@launch
                }
                messageLimit.update { it + 30 }
                remoteMessages.first { it.size > currentCount }
            } finally {
                _isLoadingMore.value = false
            }
        }
    }

    private fun markAsRead() {
        viewModelScope.launch {
            messageRepository.markConversationAsRead(roomId, studentId!!)
        }
    }

    fun onMessageChange(message: String) {
        updateState { copy(message = message) }
    }

    fun onSend(fileName: String?, fileSize: String?) {
        val text = uiState.value.message.trim()
        val imageUri = uiState.value.selectedImageUri
        val imageBytes = uiState.value.selectedImageBytes
        val fileUri = uiState.value.selectedFileUri
        val fileBytes = uiState.value.selectedFileBytes

        if (text.isBlank() && imageBytes == null && fileBytes == null) return

        updateState {
            copy(
                message = "",
                selectedImageUri = null,
                selectedImageBytes = null,
                selectedFileUri = null,
                selectedFileBytes = null
            )
        }

        if (imageBytes != null) {
            sendImageMessage(
                imageUri = imageUri,
                imageBytes = imageBytes,
                caption = text.ifBlank { null }
            )
        } else if (
            fileBytes != null
        ) {
            sendFileMessage(
                fileUri = fileUri,
                fileBytes = fileBytes,
                caption = text.ifBlank { null },
                fileName = fileName,
                fileSize = fileSize,
            )
        } else {
            sendTextMessage(text)
        }
    }

    private fun sendFileMessage(
        fileUri: Uri?,
        fileBytes: ByteArray,
        fileName: String?,
        fileSize: String?,
        caption: String?
    ) {
        val tempId = UUID.randomUUID().toString()
        val pendingMessage = MessageUiState(
            id = tempId,
            senderId = studentId!!,
            text = caption,
            fileUrl = fileUri?.toString(),
            type = MessageType.FILE.name,
            timestamp = System.currentTimeMillis(),
            isMe = true,
            status = MessageStatus.SENDING,
            fileName = fileName,
            fileSize = fileSize
        )
        pendingMessages.update { it + pendingMessage }

        viewModelScope.launch {
            messageRepository.sendFileMessage(
                roomId = roomId,
                senderId = studentId,
                fileBytes = fileBytes,
                caption = caption,
                fileName = fileName ?: fileUri?.lastPathSegment ?: "File",
                fileSize = fileSize ?: "36kb"
            )
        }
    }

    private fun sendTextMessage(text: String) {
        val tempId = UUID.randomUUID().toString()
        val pendingMessage = MessageUiState(
            id = tempId,
            senderId = studentId!!,
            text = text,
            type = MessageType.TEXT.name,
            timestamp = System.currentTimeMillis(),
            isMe = true,
            status = MessageStatus.SENDING
        )
        pendingMessages.update { it + pendingMessage }

        viewModelScope.launch {
            messageRepository.sendMessage(roomId, studentId, text)
        }
    }

    private fun sendImageMessage(imageUri: Uri?, imageBytes: ByteArray, caption: String?) {
        val tempId = UUID.randomUUID().toString()
        val pendingMessage = MessageUiState(
            id = tempId,
            senderId = studentId!!,
            text = caption,
            fileUrl = imageUri?.toString(),
            type = MessageType.IMAGE.name,
            timestamp = System.currentTimeMillis(),
            isMe = true,
            status = MessageStatus.SENDING
        )
        pendingMessages.update { it + pendingMessage }

        viewModelScope.launch {
            messageRepository.sendImageMessage(
                roomId = roomId,
                senderId = studentId,
                imageBytes = imageBytes,
                caption = caption
            )
        }
    }

    fun onImageSelected(uri: Uri?, context: Context) {
        if (uri == null) {
            updateState { copy(selectedImageUri = null, selectedImageBytes = null) }
            return
        }

        updateState { copy(selectedImageUri = uri) }

        viewModelScope.launch(Dispatchers.IO) {
            val bytes = context.contentResolver.openInputStream(uri)?.readBytes()
            updateState { copy(selectedImageBytes = bytes) }
        }
    }

    fun onRemoveImage() {
        updateState { copy(selectedImageUri = null) }
    }

    fun onFileSelected(uri: Uri?, context: Context) {
        if (uri == null) {
            updateState { copy(selectedFileUri = null, selectedFileBytes = null) }
            return
        }

        updateState { copy(selectedFileUri = uri) }
        viewModelScope.launch(Dispatchers.IO) {
            val bytes = context.contentResolver.openInputStream(uri)?.readBytes()
            updateState { copy(selectedFileBytes = bytes) }
        }
    }

    fun onRemoveFile() {
        updateState { copy(selectedFileUri = null) }
    }

    private fun updateState(block: MessageState.() -> MessageState) {
        _uiState.value = _uiState.value.block()
    }

    fun generateRoomId(
        user1: String,
        user2: String
    ): String {
        return listOf(user1, user2)
            .sorted()
            .joinToString("_")
    }
}