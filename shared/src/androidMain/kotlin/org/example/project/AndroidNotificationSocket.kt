package org.example.project

import android.annotation.SuppressLint
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import org.example.project.data.local.TokenStorage
import org.example.project.data.remote.api.NotificationSocket
import org.example.project.data.remote.dto.notification_payload.NotificationPayload
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompHeader
import java.util.concurrent.TimeUnit

class AndroidNotificationSocket(
    private val json: Json,
    private val tokenStorage: TokenStorage
) : NotificationSocket {

    private val _notifications = MutableSharedFlow<NotificationPayload>(
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private var stompClient: StompClient? = null
    private val subscribedDestinations = mutableSetOf<String>()

    // Reconnect config
    private var reconnectJob: Job? = null
    private var isConnected = false
    private var shouldReconnect = false
    private val reconnectScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override suspend fun connect() {
        shouldReconnect = true
        connectInternal()
    }

    @SuppressLint("CheckResult")
    private fun connectInternal() {
        val token = tokenStorage.getAccessToken()

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(request)
            }
            .pingInterval(10, TimeUnit.SECONDS)
            .build()

        stompClient = Stomp.over(
            Stomp.ConnectionProvider.OKHTTP,
            "wss://tl-connect-app-latest.onrender.com/ws/websocket",
            mapOf("Origin" to "https://tl-connect-app-latest.onrender.com"),
            okHttpClient
        )

        stompClient?.lifecycle()
            ?.subscribe(
                { event ->
                    Log.d("STOMP_NOTI", "event=${event.type}")
                    when (event.type) {
                        LifecycleEvent.Type.OPENED -> {
                            Log.d("STOMP_NOTI", "Connected!")
                            isConnected = true
                            reconnectJob?.cancel()
                            subscribedDestinations.forEach { resubscribe(it) }
                        }
                        LifecycleEvent.Type.ERROR -> {
                            Log.e("STOMP_NOTI", "connection error", event.exception)
                            isConnected = false
                            scheduleReconnect()
                        }
                        LifecycleEvent.Type.CLOSED -> {
                            Log.d("STOMP_NOTI", "Disconnected")
                            isConnected = false
                            if (shouldReconnect) scheduleReconnect()
                        }
                        else -> Unit
                    }
                },
                {
                    Log.e("STOMP_NOTI", "lifecycle error", it)
                    isConnected = false
                    scheduleReconnect()
                }
            )

        stompClient?.connect(
            listOf(
                StompHeader("Authorization", "Bearer $token"),
                StompHeader("accept-version", "1.1,1.2"),
                StompHeader("heart-beat", "10000,10000")
            )
        )
    }

    private fun scheduleReconnect() {
        if (!shouldReconnect) return
        if (reconnectJob?.isActive == true) return

        reconnectJob = reconnectScope.launch {
            var delayMs = 2000L
            repeat(5) { attempt ->
                Log.d("STOMP_NOTI", "Reconnect attempt ${attempt + 1}, delay=${delayMs}ms")
                delay(delayMs)
                if (!shouldReconnect) return@launch

                try { stompClient?.disconnect() } catch (_: Exception) {}
                stompClient = null

                connectInternal()
                delayMs = (delayMs * 1.5).toLong().coerceAtMost(15000L)
            }
        }
    }

    override suspend fun disconnect() {
        shouldReconnect = false
        reconnectJob?.cancel()
        reconnectJob = null
        isConnected = false
        subscribedDestinations.clear()
        stompClient?.disconnect()
        stompClient = null
    }

    override fun notifications(): Flow<NotificationPayload> = _notifications

    @SuppressLint("CheckResult")
    override fun subscribe(destination: String) {
        subscribedDestinations.add(destination)
        resubscribe(destination)
    }

    override fun reconnectIfNeeded() {
        if (isConnected) return

        if (reconnectJob?.isActive == true) {
            reconnectJob?.cancel()
            reconnectJob = null
        }

        reconnectScope.launch {
            try { stompClient?.disconnect() } catch (_: Exception) {}
            stompClient = null

            Log.d("STOMP_PAYMENT", "Ép buộc kết nối lại ngay lập tức từ UI Lifecycle!")
            connectInternal()
        }
    }

    @SuppressLint("CheckResult")
    private fun resubscribe(destination: String) {
        stompClient?.topic(destination)
            ?.subscribe(
                { message ->
                    try {
                        val payload = json.decodeFromString<NotificationPayload>(message.payload)
                        _notifications.tryEmit(payload)
                    } catch (e: Exception) {
                        Log.e("STOMP_NOTI", "parse error", e)
                    }
                },
                { Log.e("STOMP_NOTI", "topic error: $destination", it) }
            )
    }
}