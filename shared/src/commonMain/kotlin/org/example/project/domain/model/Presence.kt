package org.example.project.domain.model

data class Presence(
    val isOnline: Boolean = false,
    val lastSeen: Long = 0
)
