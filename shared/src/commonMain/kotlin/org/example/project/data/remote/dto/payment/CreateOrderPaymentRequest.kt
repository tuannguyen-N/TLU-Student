package org.example.project.data.remote.dto.payment

import kotlinx.serialization.Serializable

@Serializable
data class CreateOrderPaymentRequest(
    val invoiceId: Int,
    val provider: String,
    val language: String,
    val bankCode: String,
    val ipAddress: String
)
