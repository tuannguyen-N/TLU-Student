package org.example.project.data.remote.api

import kotlinx.coroutines.flow.Flow
import org.example.project.data.remote.dto.notification_payload.NotificationPayload

interface NotificationSocket {
    suspend fun connect()

    suspend fun disconnect()

    fun notifications(): Flow<NotificationPayload>

    fun subscribe(destination: String)

    fun reconnectIfNeeded()
}