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
import org.example.project.DeviceProvider
import org.example.project.domain.usecase.HandleLoginSuccessUseCase
import org.example.project.presentations.utils.MsalHelper

class LoginViewModel(
    private val handleLoginSuccessUseCase: HandleLoginSuccessUseCase,
    private val deviceProvider: DeviceProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginState())
    val uiState = _uiState.asStateFlow()

    private val _event = Channel<LoginUiEvent>(Channel.BUFFERED)
    val event = _event.receiveAsFlow()

    fun onLoginClick(activity: Activity) {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }

            MsalHelper.checkExistingAccount(
                onSuccess = { _, token ->
                    Log.e("123123", "onLoginClick: $token")
                    onSignMsalSuccess(token, deviceProvider.getDeviceId())
                },
                onRequireLogin = {
                    MsalHelper.signOut {
                        MsalHelper.signIn(activity) { newToken, isNoInternet ->
                            if (newToken != null) {
                                onSignMsalSuccess(newToken, deviceProvider.getDeviceId())
                            } else if (isNoInternet) {
                                sendUiEvent(LoginUiEvent.ShowNoInternetDialog)
                                updateState { copy(isLoading = false) }
                            } else {
                                updateState {
                                    copy(error = "Đăng nhập thất bại")
                                }
                                updateState { copy(isLoading = false) }
                            }
                        }
                    }
                }
            )
        }
    }

    fun onSignMsalSuccess(token: String, deviceId: String) {
        viewModelScope.launch {
            handleLoginSuccessUseCase(token, deviceId).fold(
                onSuccess = {
                    sendUiEvent(LoginUiEvent.OnLoginSuccess)
                },
                onFailure = {
                    Log.e("123123", "onSignMsalSuccess: $it")
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