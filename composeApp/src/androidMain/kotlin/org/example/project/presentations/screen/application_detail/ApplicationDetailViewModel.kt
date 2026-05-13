package org.example.project.presentations.screen.application_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.model.AppResult
import org.example.project.domain.repository.ApplicationRepository

class ApplicationDetailViewModel(
    private val repository: ApplicationRepository,
    private val id: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(ApplicationDetailState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchApplicationDetail()
    }

    private fun fetchApplicationDetail() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            when (val result = repository.getApplicationDetail(id)) {
                is AppResult.Success -> {
                    _uiState.update { it.copy(isLoading = false, data = result.data) }
                }

                is AppResult.Failure -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
            }
        }
    }
}
