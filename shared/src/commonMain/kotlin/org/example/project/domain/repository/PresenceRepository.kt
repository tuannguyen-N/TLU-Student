package org.example.project.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.model.Presence

interface PresenceRepository {
    fun setupPresence(studentId: String)
    fun goOnline(studentId: String)
    fun goOffline(studentId: String)
    fun observeOnlineStatus(userId: String): Flow<Boolean>
    fun observePresence(userId: String): Flow<Presence>
    fun observeAllPresence(): Flow<Map<String, Presence>>
}