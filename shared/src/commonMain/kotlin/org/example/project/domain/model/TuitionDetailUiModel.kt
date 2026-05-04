package org.example.project.domain.model

import org.example.project.data.remote.dto.tuition_detail.TuitionItem

data class TuitionDetailUiModel(
    val tuitionId: Int,
    val dueDate: String,
    val finalAmount: String,
    val items: List<TuitionItem>,
    val status: PaymentStatus,
    val totalAmount: String,
    val semester: String
)
