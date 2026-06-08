package org.example.project.domain.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.example.project.data.remote.api.PaymentApi
import org.example.project.data.remote.api.PaymentSocket
import org.example.project.data.remote.dto.PaymentStatusPayload.PaymentStatusPayload
import org.example.project.data.remote.dto.Response
import org.example.project.data.remote.dto.payment.CreateOrderPaymentData
import org.example.project.domain.model.AppResult

class PaymentRepository(
    private val paymentApi: PaymentApi,
    private val paymentSocket: PaymentSocket
) {
    private val _paymentEvents = MutableSharedFlow<PaymentStatusPayload>()

    val paymentEvents = _paymentEvents.asSharedFlow()

    suspend fun createOrderPayment(
        invoiceId: Int, provider: String
    ): AppResult<CreateOrderPaymentData> {
        return try {
            val data = paymentApi.createOrderPayment(invoiceId, provider).data
                ?: throw Exception("Failed to create order payment")
            AppResult.Success(data)
        } catch (e: Exception) {
            AppResult.Failure(e.message, e)
        }
    }

    suspend fun paymentReturn(
        tuitionId: Int
    ): AppResult<Response> {
        return try {
            val data = paymentApi.paymentReturn(tuitionId)
            AppResult.Success(data)
        } catch (e: Exception) {
            AppResult.Failure(e.message, e)
        }
    }

    private var paymentJob: Job? = null

    fun startRealtime() {
        if (paymentJob?.isActive == true) return

        paymentJob = CoroutineScope(
            SupervisorJob() + Dispatchers.Default
        ).launch {
            paymentSocket.connect()
            paymentSocket.subscribe("/user/queue/payment")
            paymentSocket.events().collect { _paymentEvents.emit(it) }
        }
    }

    suspend fun stopRealtime() {
        paymentJob?.cancel()
        paymentJob = null
        paymentSocket.disconnect()
    }
}