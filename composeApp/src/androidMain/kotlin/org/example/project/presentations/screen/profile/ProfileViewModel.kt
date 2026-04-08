package org.example.project.presentations.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.data.remote.interceptor.AuthPluginConfig
import org.example.project.domain.usecase.StudentUseCase

class ProfileViewModel(
    private val studentUseCase: StudentUseCase,
    private val authPluginConfig: AuthPluginConfig
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileState())
    val uiState = _uiState.asStateFlow()

    private val _event = Channel<ProfileUiEvent>()
    val event = _event.receiveAsFlow()

    init {
        observeStudentInfo()
        loadImage()
    }

    private fun loadImage() {
        val image = authPluginConfig.imageStorage.getImageBase64()
        updateState { copy(avatarBase64 = image) }
    }

    private fun observeStudentInfo() {
        viewModelScope.launch {
            studentUseCase.studentInfo.collect { studentData ->
                updateState { copy(studentInfo = studentData) }
            }
        }
    }

    private fun updateState(newState: ProfileState.() -> ProfileState) {
        _uiState.update(newState)
    }

    private fun sendUiEvent(event: ProfileUiEvent) {
        _event.trySend(event)
    }
}
