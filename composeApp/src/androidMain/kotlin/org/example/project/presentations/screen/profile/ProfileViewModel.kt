package org.example.project.presentations.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.model.ProfileState
import org.example.project.domain.usecase.StudentUseCase

class ProfileViewModel(
    private val studentUseCase: StudentUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileState())
    val uiState = _uiState.asStateFlow()

    private val _event = Channel<ProfileUiEvent>()
    val event = _event.receiveAsFlow()

    init {
        observeStudentInfo()
    }

    private fun observeStudentInfo(){
        viewModelScope.launch {
            studentUseCase.studentInfo.collect { studentInformation ->
                updateState { copy(studentInfo = studentInformation) }
            }
        }
    }

    private fun loadFromApi() {
        viewModelScope.launch {
//            updateState { copy(isLoading = true) }
            studentUseCase.getStudentInfo().fold(
                onSuccess = {
                    // TODO:  
                },
                onFailure = {
                    // TODO:  
                }
            )
        }
    }

    private fun updateState(newState: ProfileState.() -> ProfileState) {
        _uiState.update(newState)
    }

    private fun sendUiEvent(event: ProfileUiEvent) {
        _event.trySend(event)
    }
}
