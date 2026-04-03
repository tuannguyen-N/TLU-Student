package org.example.project.domain.model

data class TuitionUiModel(
    val dueDate: String,
    val finalAmount: String,
    val invoiceId: Int,
    val semesterName: String,
    val status: PaymentStatus,
    val totalAmount: String
)