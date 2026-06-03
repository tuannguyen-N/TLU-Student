package org.example.project.domain.repository

import org.example.project.domain.model.User

interface UserRepository {
    suspend fun updateOnlineStatus(
        isOnline: Boolean,
        currentUserId: String
    )

    suspend fun getUsers(
        size: Int,
        excludeUserId: String? = null
    ): List<User>
}