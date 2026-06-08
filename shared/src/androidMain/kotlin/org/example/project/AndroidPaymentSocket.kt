package org.example.project

import android.annotation.SuppressLint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.serialization.json.Json
import org.example.project.data.local.TokenStorage
import org.example.project.data.remote.api.PaymentSocket
import org.example.project.data.remote.dto.PaymentStatusPayload.PaymentStatusPayload
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.StompHeader

class AndroidPaymentSocket(
    private val json: Json,
    private val tokenStorage: TokenStorage
) : PaymentSocket {

    private val _events = MutableSharedFlow<PaymentStatusPayload>()

    private val stompClient = Stomp.over(
        Stomp.ConnectionProvider.OKHTTP,
        "wss://tl-connect-app-latest.onrender.com/ws"
    )

    override suspend fun connect() {
        val token = tokenStorage.getAccessToken()

        stompClient.connect(
            listOf(
                StompHeader(
                    "Authorization",
                    "Bearer $token"
                )
            )
        )
    }

    override suspend fun disconnect() {
        stompClient.disconnect()
    }

    @SuppressLint("CheckResult")
    override fun subscribe(destination: String) {
        stompClient.topic(destination)
            .subscribe { message ->

                runCatching {

                    json.decodeFromString<PaymentStatusPayload>(
                        message.payload
                    )

                }
                    .onSuccess { _events.tryEmit(it) }
                    .onFailure { it.printStackTrace() }
            }
    }

    override fun events(): Flow<PaymentStatusPayload> {
        return _events
    }
}