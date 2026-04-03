package org.example.project.data.mapper

import org.example.project.data.remote.dto.tuition.TuitionData
import org.example.project.domain.model.PaymentStatus
import org.example.project.domain.model.TuitionUiModel

fun List<TuitionData>.toUiModel(): List<TuitionUiModel>{
    return this.map { data -> TuitionUiModel(
        dueDate = data.dueDate.toDisplayDate(),
        finalAmount = data.finalAmount.toLong().toFormatAmountAndD(),
        invoiceId = data.invoiceId,
        semesterName = data.semesterName,
        status = data.status.toTuitionStatus(),
        totalAmount = data.totalAmount.toLong().toFormatAmountAndD()
    ) }
}

fun String.toTuitionStatus(): PaymentStatus{
    return when(this){
        "PAID" -> PaymentStatus.PAID
        "UNPAID" -> PaymentStatus.UNPAID
        else -> PaymentStatus.UNPAID
    }
}