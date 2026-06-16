package org.example.project.domain.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.example.project.data.local.dao.PaymentStatusDao
import org.example.project.data.local.entity.PaymentStatusEntity
import org.example.project.data.remote.api.PaymentApi
import org.example.project.data.remote.api.PaymentSocket
import org.example.project.data.remote.dto.Response
import org.example.project.data.remote.dto.payment.CreateOrderPaymentData
import org.example.project.domain.model.AppResult

class PaymentRepository(
    private val paymentApi: PaymentApi,
    private val paymentSocket: PaymentSocket,
    private val paymentStatusDao: PaymentStatusDao,
    private val tuitionRepository: TuitionRepository
) {
    private var paymentJob: Job? = null

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

    fun getPaymentStatus(tuitionId: Int): Flow<PaymentStatusEntity?> =
        paymentStatusDao.getByTuitionId(tuitionId)

    fun startRealtime() {
        if (paymentJob?.isActive == true) return

        paymentJob = CoroutineScope(SupervisorJob() + Dispatchers.Default).launch {
            paymentSocket.connect()
            paymentSocket.events().collect { payload ->
                paymentStatusDao.insert(
                    PaymentStatusEntity(
                        transactionCode = payload.transactionCode,
                        userId = payload.userId,
                        tuitionId = payload.tuitionId,
                        status = payload.status
                    )
                )
            }
        }
    }

    suspend fun reconnectIfNeeded() {
        if (paymentJob?.isActive == true) {
            paymentSocket.reconnectIfNeeded()
            tuitionRepository.getTuition()
        } else {
            startRealtime()
        }
    }

    suspend fun stopRealtime() {
        paymentJob?.cancel()
        paymentJob = null
        paymentSocket.disconnect()
    }
}