package org.example.project.data.remote.api

import kotlinx.coroutines.flow.Flow
import org.example.project.data.remote.dto.PaymentStatusPayload.PaymentStatusPayload

interface PaymentSocket {
    suspend fun connect()
    suspend fun disconnect()
    fun subscribe(destination: String)
    fun events(): Flow<PaymentStatusPayload>
}