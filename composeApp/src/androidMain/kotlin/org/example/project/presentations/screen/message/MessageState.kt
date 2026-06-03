package org.example.project.presentations.screen.message

import android.net.Uri
import org.example.project.domain.model.User

data class MessageState(
    val message: String = "",
    val isLoading: Boolean = false,
    val selectedImageUri: Uri? = null,
    val chatUser: User? = null
)