package org.example.project.data.remote.dto.tuition_detail

import kotlinx.serialization.Serializable

@Serializable
data class TuitionDetailData(
    val dueDate: String,
    val finalAmount: Double,
    val invoiceId: Int,
    val items: List<TuitionItem>,
    val semesterName: String,
    val status: String,
    val totalAmount: Double
)