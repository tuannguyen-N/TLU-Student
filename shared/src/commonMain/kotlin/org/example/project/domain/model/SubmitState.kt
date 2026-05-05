package org.example.project.domain.model

sealed interface SubmitState {
    object Idle: SubmitState
    object Loading: SubmitState
    object Success: SubmitState
    data class Error(val message: String): SubmitState
}