package org.example.project.presentations.screen.message

import android.net.Uri
import org.example.project.data.remote.dto.student_search.StudentData

data class MessageState(
    val message: String = "",
    val isLoading: Boolean = true,
    val selectedImageUri: Uri? = null,
    val selectedImageBytes: ByteArray? = null,

    val selectedFileUri: Uri? = null,
    val selectedFileBytes: ByteArray? = null,
    val chatStudent: StudentData? = null,
)