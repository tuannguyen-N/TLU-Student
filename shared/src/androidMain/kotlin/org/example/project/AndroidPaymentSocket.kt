package org.example.project

import android.annotation.SuppressLint
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import org.example.project.data.local.TokenStorage
import org.example.project.data.remote.api.PaymentSocket
import org.example.project.data.remote.dto.PaymentStatusPayload.PaymentStatusPayload
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompHeader

class AndroidPaymentSocket(
    private val json: Json,
    private val tokenStorage: TokenStorage
) : PaymentSocket {

    private val _events = MutableSharedFlow<PaymentStatusPayload>()
    private var stompClient: StompClient? = null

    @SuppressLint("CheckResult")
    override suspend fun connect() {
        val token = tokenStorage.getAccessToken()

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(request)
            }
            .build()

        stompClient = Stomp.over(
            Stomp.ConnectionProvider.OKHTTP,
            "wss://tl-connect-app-latest.onrender.com/ws",
            null,
            okHttpClient
        )

        stompClient?.lifecycle()
            ?.subscribe(
                { event ->
                    Log.d("STOMP_PAYMENT", "event=${event.type}")
                    if (event.type == LifecycleEvent.Type.ERROR) {
                        Log.e("STOMP_PAYMENT", "connection error", event.exception)
                    }
                },
                { Log.e("STOMP_PAYMENT", "lifecycle error", it) }
            )

        stompClient!!.connect(
            listOf(
                StompHeader("Authorization", "Bearer $token"),
                StompHeader("accept-version", "1.1,1.2"),
                StompHeader("heart-beat", "0,0")
            )
        )
    }

    override suspend fun disconnect() {
        stompClient?.disconnect()
        stompClient = null
    }

    override fun events(): Flow<PaymentStatusPayload> = _events

    @SuppressLint("CheckResult")
    override fun subscribe(destination: String) {
        stompClient?.topic(destination)
            ?.subscribe { message ->
                try {
                    val payload = json.decodeFromString<PaymentStatusPayload>(message.payload)
                    _events.tryEmit(payload)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
    }
}