package org.example.project.presentations.utils

import kotlinx.coroutines.flow.MutableStateFlow

object ChatPresenceManager {
    val currentRoom = MutableStateFlow<String?>(null)
}