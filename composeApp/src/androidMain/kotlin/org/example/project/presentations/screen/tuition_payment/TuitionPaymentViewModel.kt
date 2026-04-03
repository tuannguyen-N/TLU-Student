package org.example.project.presentations.screen.tuition_payment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.domain.repository.TuitionRepository
import org.example.project.presentations.utils.withDelayedLoading

class TuitionPaymentViewModel(
    private val tuitionRepository: TuitionRepository
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
                        loadDetailTuition(result[0].invoiceId)
                    },
                    onFailure = {
                        Log.e("tuition_error", "loadData: $it")
                    }
                )
            }
        }
    }

    private suspend fun loadDetailTuition(invoiceId: Int) {
        tuitionRepository.getDetailTuition(invoiceId).fold(
            onSuccess = {
                updateState { copy(currentTuitionDetail = it) }
            },
            onFailure = {
                Log.e("tuition_error", "loadDetailTuition: $it")
            }
        )
    }


    fun onChangeTab(value: Int) {
        updateState { copy(selectedTab = value) }
    }

    private fun updateState(block: TuitionStatus.() -> TuitionStatus) {
        _uiState.value = _uiState.value.block()
    }
}