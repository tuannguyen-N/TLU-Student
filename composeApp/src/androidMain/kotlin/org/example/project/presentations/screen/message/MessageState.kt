package org.example.project.presentations.screen.message

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import org.example.project.data.remote.dto.student_search.StudentData
import org.example.project.domain.model.MessageUiState
import org.example.project.domain.model.User
import org.example.project.domain.model.UserUiModel

data class MessageState(
    val message: TextFieldValue = TextFieldValue(""),
    val isLoading: Boolean = true,

    val isLoadingMore: Boolean = false,
    val hasMoreMessages: Boolean = false,

    val pendingMessages: List<MessageUiState> = emptyList(),
    val olderMessages: List<MessageUiState> = emptyList(),

    val selectedImageUri: Uri? = null,
    val selectedImageBytes: ByteArray? = null,

    val selectedFileUri: Uri? = null,
    val selectedFileBytes: ByteArray? = null,

    val selectedVideoUri: Uri? = null,
    val selectedVideoBytes: ByteArray? = null,

    val chatStudent: StudentData? = null,
    val chatUser: UserUiModel? = null,

    val isAiReplying: Boolean = false
)