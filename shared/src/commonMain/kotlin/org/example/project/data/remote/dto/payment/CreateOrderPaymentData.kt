package org.example.project.data.remote.dto.payment

import kotlinx.serialization.Serializable

@Serializable
data class CreateOrderPaymentData(
    val amount: Double,
    val invoiceStatus: String,
    val orderUrl: String,
    val transactionCode: String
)