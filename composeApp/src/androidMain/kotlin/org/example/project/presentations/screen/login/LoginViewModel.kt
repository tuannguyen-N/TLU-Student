package org.example.project.presentations.screen.login

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
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

    fun onLoginClick(activity: Activity){
        MsalHelper.signIn(activity) { token ->
            if (token != null) onSignMsalSuccess(token)
        }
    }

    fun onSignMsalSuccess(token: String) {
        Log.e("123123", "onSignMsalSuccess: $token", )
        viewModelScope.launch {
            updateState { copy(isLoading = true) }

            loginUseCase(token).fold(
                onSuccess = {
                    updateState { copy(isLoading = false) }
                    sendUiEvent(LoginUiEvent.OnNavigateToHome)
                },
                onFailure = {
                    updateState { copy(isLoading = false) }
                    sendUiEvent(LoginUiEvent.ShowError)
                }
            )
        }
    }

    private fun updateState(newState: LoginState.() -> LoginState) {
        _uiState.update(newState)
    }

    private fun sendUiEvent(event: LoginUiEvent) {
        _event.trySend(event)
    }
}