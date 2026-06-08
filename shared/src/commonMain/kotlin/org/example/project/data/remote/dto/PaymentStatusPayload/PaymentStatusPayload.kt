package org.example.project.data.remote.dto.PaymentStatusPayload

import kotlinx.serialization.Serializable

@Serializable
data class PaymentStatusPayload(
    val userId: Int,
    val tuitionId: Int,
    val transactionCode: String,
    val status: String
)