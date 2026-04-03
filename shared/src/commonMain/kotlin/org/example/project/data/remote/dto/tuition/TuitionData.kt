package org.example.project.data.remote.dto.tuition

import kotlinx.serialization.Serializable

@Serializable
data class TuitionData(
    val invoiceId: Int,
    val semesterName: String,
    val totalAmount: Double,
    val finalAmount: Double,
    val status: String,
    val dueDate: String,
)