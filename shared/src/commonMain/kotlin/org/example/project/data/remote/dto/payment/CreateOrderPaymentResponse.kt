package org.example.project.data.remote.dto.payment

import kotlinx.serialization.Serializable

@Serializable
data class CreateOrderPaymentResponse(
    val code: Int,
    val data: CreateOrderPaymentData?,
    val message: String
)