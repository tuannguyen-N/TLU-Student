package org.example.project.presentations.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.example.project.domain.model.PaymentResult

object PaymentDeepLinkEvent {
    private val _result = MutableSharedFlow<PaymentResult>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val result: SharedFlow<PaymentResult> = _result.asSharedFlow()

    fun emit(responseCode: String?, txnRef: String?) {
        val event = when (responseCode) {
            "00" -> PaymentResult.Success(txnRef)
            else -> PaymentResult.Failure(responseCode)
        }
        _result.tryEmit(event)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun clear() {
        _result.resetReplayCache()
    }
}