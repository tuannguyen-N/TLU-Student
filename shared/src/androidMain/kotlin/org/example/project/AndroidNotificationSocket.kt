package org.example.project

import android.annotation.SuppressLint
import android.util.Log
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import org.example.project.data.local.TokenStorage
import org.example.project.data.remote.api.NotificationSocket
import org.example.project.data.remote.dto.notification_payload.NotificationPayload
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompHeader

class StompNotificationSocket(
    private val json: Json,
    private val tokenStorage: TokenStorage
) : NotificationSocket {

    private val _notifications = MutableSharedFlow<NotificationPayload>(
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

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
            "https://tl-connect-app-latest.onrender.com/ws/websocket",
            null,
            okHttpClient
        )

        stompClient?.lifecycle()
            ?.subscribe(
                { event ->
                    Log.d("STOMP", "event=${event.type}")
                    if (event.type == LifecycleEvent.Type.ERROR) {
                        Log.e("STOMP", "connection error", event.exception)
                    }
                },
                { Log.e("STOMP", "lifecycle error", it) }
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

    override fun notifications(): Flow<NotificationPayload> = _notifications

    @SuppressLint("CheckResult")
    override fun subscribe(destination: String) {
        stompClient?.topic(destination)
            ?.subscribe { message ->
                try {
                    val payload = json.decodeFromString<NotificationPayload>(message.payload)
                    _notifications.tryEmit(payload)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
    }
}