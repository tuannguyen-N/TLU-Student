package org.example.project.presentations.screen.message

import org.example.project.domain.model.User

data class MessageState(
    val message: String = "",
    val isLoading: Boolean = false,
    val chatUser: User? = null,
)