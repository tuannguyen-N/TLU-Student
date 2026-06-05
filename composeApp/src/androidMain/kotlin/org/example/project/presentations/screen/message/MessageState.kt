package org.example.project.presentations.screen.message

import android.net.Uri
import org.example.project.data.remote.dto.student_search.StudentData
import org.example.project.domain.model.MessageUiState
import org.example.project.domain.model.User

data class MessageState(
    val message: String = "",
    val isLoading: Boolean = true,

    val isLoadingMore: Boolean = false,
    val hasMoreMessages: Boolean = true,

    val pendingMessages: List<MessageUiState> = emptyList(),
    val olderMessages: List<MessageUiState> = emptyList(),

    val selectedImageUri: Uri? = null,
    val selectedImageBytes: ByteArray? = null,

    val selectedFileUri: Uri? = null,
    val selectedFileBytes: ByteArray? = null,

    val chatStudent: StudentData? = null,
    val chatUser: User? = null
)