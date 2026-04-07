package org.example.project.presentations.screen.class_signed_up

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ClassSignedUpViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ClassSignedUpState())
    val uiState = _uiState.asStateFlow()
}