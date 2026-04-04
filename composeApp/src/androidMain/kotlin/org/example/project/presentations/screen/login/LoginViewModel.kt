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
            MsalHelper.signOut {}
            delay(1000L)
            MsalHelper.signIn(activity) { newToken, isNoInternet ->
                if (newToken != null)
                    onSignMsalSuccess(newToken)
                else if (isNoInternet) {
                    updateState { copy(isLoading = false, error = "Không có kết nối internet") }
                    sendUiEvent(LoginUiEvent.ShowNoInternetDialog)
                } else
                    updateState { copy(isLoading = false, error = "Đăng nhập thất bại") }

                Log.e("123123", "onLoginClick: $newToken")
            }
        }
    }

    fun onSignMsalSuccess(token: String) {
        viewModelScope.launch {
            loginUseCase(token).fold(
                onSuccess = {
                    sendUiEvent(LoginUiEvent.OnNavigateToHome)
                },
                onFailure = {
                    updateState { copy(showErrorSheet = true) }
                }
            )
            updateState { copy(isLoading = false) }
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