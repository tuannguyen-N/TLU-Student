package org.example.project.presentations.screen.tuition_payment

import org.example.project.data.remote.dto.tuition_detail.TuitionItem
import org.example.project.domain.model.PaymentType
import org.example.project.domain.model.TuitionUiModel

sealed interface TuitionUiEvent {
    data class ShowDetailTuitionCourseDialog(val courses: List<TuitionItem>) : TuitionUiEvent
    data class SelectTuition(val tuition: TuitionUiModel) : TuitionUiEvent
    data class NavigateToPayment(val tuition: TuitionUiModel) : TuitionUiEvent
    data class SelectPaymentType(val type: PaymentType) : TuitionUiEvent
    object DismissDialog : TuitionUiEvent
    object NavigateBack : TuitionUiEvent
}