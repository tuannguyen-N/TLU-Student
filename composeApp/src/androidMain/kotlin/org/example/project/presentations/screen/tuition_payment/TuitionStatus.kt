package org.example.project.presentations.screen.tuition_payment

import org.example.project.domain.model.PaymentType
import org.example.project.domain.model.TuitionDetailUiModel
import org.example.project.domain.model.TuitionUiModel

data class TuitionStatus(
    val allTuition: List<TuitionUiModel>? = null,
    val currentTuitionDetail: TuitionDetailUiModel? = null,
    val selectedTuitionDetail: TuitionDetailUiModel? = null,

    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isRefreshingDetail: Boolean = false,
    val isShowDetailTuitionCourseDialog: Boolean = false,
    val isInPaymentScreen: Boolean = false,
    val selectedPaymentType: PaymentType = PaymentType.VN_PAY,
    val isProcessingPayment: Boolean = false,
    val paymentUrl: String? = null
)
