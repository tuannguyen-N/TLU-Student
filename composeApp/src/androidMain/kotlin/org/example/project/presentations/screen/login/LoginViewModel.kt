package org.example.project.presentations.screen.login

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.model.LoginState
import org.example.project.domain.usecase.LoginUseCase
import org.example.project.presentations.utils.MsalHelper

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginState())
    val uiState = _uiState.asStateFlow()

    private val _event = Channel<LoginUiEvent>()
    val event = _event.receiveAsFlow()

    fun onLoginClick(activity: Activity) {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            MsalHelper.signOut {}//todo
            delay(1000L)
            MsalHelper.signIn(activity) { newToken ->
                Log.e("123123", "onLoginClick: $newToken", )
                if (newToken != null) onSignMsalSuccess(newToken)
            }
            updateState { copy(isLoading = false) }
        }
    }

    fun onSignMsalSuccess(token: String) {
        viewModelScope.launch {
            loginUseCase(token).fold(
                onSuccess = {
                    sendUiEvent(LoginUiEvent.OnNavigateToHome)
                },
                onFailure = {
                    Log.e("123123", "onSignMsalSuccess: $it", )
                    updateState { copy(showErrorSheet = true) }
                }
            )
        }
    }

    fun onDismissErrorSheet() {
        updateState { copy(showErrorSheet = false) }
    }

    private fun updateState(newState: LoginState.() -> LoginState) {
        _uiState.update(newState)
    }

    private fun sendUiEvent(event: LoginUiEvent) {
        _event.trySend(event)
    }
}