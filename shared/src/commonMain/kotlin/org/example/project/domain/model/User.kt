package org.example.project.domain.model

data class User(
    val id: String = "",
    val name: String = "",
    val avatarUrl: String? = null,
    val fcmTokens: List<String> = emptyList()
)