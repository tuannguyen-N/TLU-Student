package org.example.project.domain.model

data class User(
    val id: String = "",
    val name: String = "",
    val avatarUrl: String? = null,
    var isOnline: Boolean = false,
    var lastSeen: Long = 0L
)