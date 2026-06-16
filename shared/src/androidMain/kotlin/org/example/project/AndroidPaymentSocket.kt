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
import org.example.project.data.remote.api.PaymentSocket
import org.example.project.data.remote.dto.PaymentStatusPayload.PaymentStatusPayload
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompHeader
import java.util.concurrent.TimeUnit

class AndroidPaymentSocket(
    private val json: Json,
    private val tokenStorage: TokenStorage
) : PaymentSocket {

    private val _events = MutableSharedFlow<PaymentStatusPayload>(
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    private var stompClient: StompClient? = null
    private var subscribeDestination: String? = null

    // Reconnect config
    private var reconnectJob: Job? = null
    private var isConnected = false
    private var shouldReconnect = false
    private val reconnectScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @SuppressLint("CheckResult")
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
                    Log.d("STOMP_PAYMENT", "event=${event.type}")
                    when (event.type) {
                        LifecycleEvent.Type.OPENED -> {
                            Log.d("STOMP_PAYMENT", "Connected!")
                            isConnected = true
                            reconnectJob?.cancel()
                            subscribeDestination?.let { subscribe(it) }
                                ?: subscribe("/user/queue/payment")
                        }
                        LifecycleEvent.Type.ERROR -> {
                            Log.e("STOMP_PAYMENT", "connection error", event.exception)
                            isConnected = false
                            scheduleReconnect()
                        }
                        LifecycleEvent.Type.CLOSED -> {
                            Log.d("STOMP_PAYMENT", "Disconnected")
                            isConnected = false
                            if (shouldReconnect) scheduleReconnect()
                        }
                        else -> Unit
                    }
                },
                {
                    Log.e("STOMP_PAYMENT", "lifecycle error", it)
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
                Log.d("STOMP_PAYMENT", "Reconnect attempt ${attempt + 1}, delay=${delayMs}ms")
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
        stompClient?.disconnect()
        stompClient = null
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

    override fun events(): Flow<PaymentStatusPayload> = _events

    @SuppressLint("CheckResult")
    override fun subscribe(destination: String) {
        subscribeDestination = destination
        stompClient?.topic(destination)
            ?.subscribe(
                { message ->
                    try {
                        val payload = json.decodeFromString<PaymentStatusPayload>(message.payload)
                        _events.tryEmit(payload)
                    } catch (e: Exception) {
                        Log.e("STOMP_PAYMENT", "parse error", e)
                    }
                },
                { Log.e("STOMP_PAYMENT", "topic error: $destination", it) }
            )
    }
}