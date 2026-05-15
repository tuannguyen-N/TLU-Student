package org.example.project.presentations.screen.tuition_payment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.domain.model.PaymentType
import org.example.project.domain.model.TuitionUiModel
import org.example.project.domain.repository.PaymentRepository
import org.example.project.domain.repository.TuitionRepository
import org.example.project.presentations.utils.withDelayedLoading

class TuitionPaymentViewModel(
    private val tuitionRepository: TuitionRepository,
    private val paymentRepository: PaymentRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(TuitionStatus())
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            withDelayedLoading(onLoading = { updateState { copy(isLoading = it) } }) {
                tuitionRepository.getTuition().fold(
                    onSuccess = { result ->
                        updateState { copy(allTuition = result) }
                    },
                    onFailure = {
                        Log.e("tuition_error", "loadData: $it")
                    }
                )
            }
        }
    }

    fun onNavigateToPayment(tuition: TuitionUiModel) {
        viewModelScope.launch {
            tuitionRepository.getDetailTuition(tuition.invoiceId).onSuccess { detail ->
                updateState {
                    copy(
                        selectedTuitionDetail = detail,
                        isInPaymentScreen = true
                    )
                }
            }
        }
    }

    fun onDismissDialog() {
        updateState { copy(isShowDetailTuitionCourseDialog = false) }
    }

    fun onSelectPaymentType(type: PaymentType) {
        updateState { copy(selectedPaymentType = type) }
    }

    fun onPay(invoiceId: Int, paymentType: PaymentType) {
        viewModelScope.launch {
            updateState { copy(isProcessingPayment = true) }
            paymentRepository.createOrderPayment(invoiceId, paymentType.value).fold(
                onSuccess = { data ->
                    updateState {
                        copy(
                            isProcessingPayment = false,
                            paymentUrl = data.orderUrl
                        )
                    }
                },
                onFailure = {
                    Log.e("payment_error", "onPay: $it")
                    updateState { copy(isProcessingPayment = false) }
                }
            )
        }
    }

    fun onPaymentUrlHandled() {
        updateState { copy(paymentUrl = null) }
    }

    fun onRefreshData() {
        viewModelScope.launch {
            updateState { copy(isRefreshing = true) }
            try {
                tuitionRepository.getTuition()
            } finally {
                kotlinx.coroutines.delay(1000L)
                updateState { copy(isRefreshing = false) }
            }
        }
    }

    fun onRefreshDetail() {
        val invoiceId = _uiState.value.selectedTuitionDetail?.tuitionId ?: return
        viewModelScope.launch {
            updateState { copy(isRefreshingDetail = true) }
            try {
                tuitionRepository.getDetailTuition(invoiceId).onSuccess { detail ->
                    updateState { copy(selectedTuitionDetail = detail) }
                }
            } finally {
                kotlinx.coroutines.delay(1000L)
                updateState { copy(isRefreshingDetail = false) }
            }
        }
    }

    fun onBackFromPayment() {
        updateState {
            copy(
                isInPaymentScreen = false,
                selectedTuitionDetail = null,
                paymentUrl = null
            )
        }
    }

    private fun updateState(block: TuitionStatus.() -> TuitionStatus) {
        _uiState.value = _uiState.value.block()
    }
}