package org.example.project.presentations.screen.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.example.project.domain.model.LoginState

class LoginViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(LoginState())
    val uiState = _uiState.asStateFlow()

    private val _event = Channel<LoginUiEvent>()
    val event = _event.receiveAsFlow()

    fun onSignMsalSuccess(token: String){
        Log.e("123123", "onSignMsalSuccess: $token")
    }

    private fun sendUiEvent(event: LoginUiEvent) {
        viewModelScope.launch {
            _event.send(event)
        }
    }
}