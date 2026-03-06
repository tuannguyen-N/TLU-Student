package org.example.project.presentations.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.model.HomeState
import org.example.project.domain.model.HomeUiEvent
import org.example.project.domain.usecase.StudentUseCase

class HomeViewModel(
    private val studentUseCase: StudentUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

    private val _event = Channel<HomeUiEvent>()
    val event = _event.receiveAsFlow()

    init {
        observeStudentInfo()
        loadData()
    }

    private fun observeStudentInfo() {
        viewModelScope.launch {
            studentUseCase.studentInfo.collect { studentInformation ->
                studentInformation?.let {
                    updateState { copy(studentInfo = it) }
                }
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
//            _uiState.update { it.copy(isLoading = true) }

            studentUseCase.getStudentInfo().fold(
                onSuccess = {
//                    _uiState.update { it.copy(isLoading = false) }
                },
                onFailure = {
//                    _uiState.update { it.copy(isLoading = false, error = it.toString()) }
                }
            )
        }
    }

    fun onAction(action: HomeAction) {
        when (action) {
            else -> Unit
        }
    }

    private fun updateState(newState: HomeState.() -> HomeState) {
        _uiState.update(newState)
    }

    private fun sendUiEvent(event: HomeUiEvent) {
        _event.trySend(event)
    }
}