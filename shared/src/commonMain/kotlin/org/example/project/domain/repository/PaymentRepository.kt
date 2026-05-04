package org.example.project.domain.repository

import org.example.project.data.remote.api.PaymentApi
import org.example.project.data.remote.dto.payment.CreateOrderPaymentData
import org.example.project.domain.model.AppResult

class PaymentRepository(
    private val paymentApi: PaymentApi
) {
    suspend fun createOrderPayment(
        invoiceId: Int,
        provider: String
    ): AppResult<CreateOrderPaymentData> {
        return try {
            val data = paymentApi.createOrderPayment(invoiceId, provider).data
                ?: throw Exception("Failed to create order payment")
            AppResult.Success(data)
        } catch (e: Exception) {
            AppResult.Failure(e.message, e)
        }
    }
}