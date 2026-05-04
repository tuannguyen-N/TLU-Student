package org.example.project.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.project.data.remote.dto.payment.CreateOrderPaymentRequest
import org.example.project.data.remote.dto.payment.CreateOrderPaymentResponse

class PaymentApi(
    private val client: HttpClient
) {

    suspend fun createOrderPayment(invoiceId: Int, provider: String): CreateOrderPaymentResponse{
        return client.post("/api/v1/payments/create-order"){
            contentType(ContentType.Application.Json)
            setBody(
                CreateOrderPaymentRequest(
                    invoiceId = invoiceId,
                    provider = provider,
                    language = "vn",
                    bankCode = "NCB",
                    ipAddress ="127.0.0.1"
                )
            )
        }.body()
    }
}