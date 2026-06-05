package org.example.project.domain.model

data class UserUiModel(
    val studentCode: String = "",
    val name: String = "",
    val avatarUrl: String? = null,
    val isOnline: Boolean = false,
    val lastSeen: Long = 0L
)