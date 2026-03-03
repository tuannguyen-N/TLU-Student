package org.example.project.presentations.screen.edit_profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.example.project.domain.model.EditProfileState
import org.example.project.presentations.utils.ValidationUtils

class EditProfileViewModel : ViewModel() {
    var state by mutableStateOf(EditProfileState())
        private set

    private val _events = Channel<EditProfileUIEvent>()
    val events = _events.receiveAsFlow()

    var oldState = state
    val isChanged: Boolean
        get() = oldState != state
    val isFormValid: Boolean
        get() = listOf(
            state.emailError,
            state.phoneError,
            state.addressError,
            state.nameContactError,
            state.phoneContactError,
            state.addressContactError
        ).all { it == null }

    val isButtonEnabled: Boolean
        get() = isChanged && isFormValid

    fun onEmailChange(value: String) {
        state = state.copy(
            email = value,
            emailError = ValidationUtils.validateEmail(value)
        )
    }

    fun onPhoneChange(value: String) {
        state = state.copy(
            phone = value,
            phoneError = ValidationUtils.validatePhone(value)
        )
    }

    fun onNameContactChange(value: String) {
        state = state.copy(
            nameContact = value,
            nameContactError = ValidationUtils.validateRequired(value, "Họ Tên người liên hệ")
        )
    }

    fun onPhoneContactChange(value: String) {
        state = state.copy(
            phoneContact = value,
            phoneContactError = ValidationUtils.validatePhone(value)
        )
    }

    fun onAddressContactChange(value: String) {
        state = state.copy(
            addressContact = value,
            addressContactError = ValidationUtils.validateRequired(value, "Địa chỉ người liên hệ")
        )
    }

    fun onAddressChange(value: String) {
        state = state.copy(
            address = value,
            addressError = ValidationUtils.validateRequired(value, "Địa chỉ")
        )
    }

    fun onCancelEditProfile(){
        if (isChanged){
            state = state.copy(isShowExitDialog = true)
        }else {
            sendUIEvent(EditProfileUIEvent.OnNavigateBack)
        }
    }

    fun onDismissExitDialog(){
        state = state.copy(isShowExitDialog = false)
    }

    fun submit() {
//        val emailError = ValidationUtils.validateEmail(state.email)
//        val phoneError = ValidationUtils.validatePhone(state.phone)
//        val nameError = ValidationUtils.validateRequired(state.name)
//        val addressError = ValidationUtils.validateRequired(state.address)
//        val nameContactError = ValidationUtils.validateName(state.nameContact)
//        val phoneContactError = ValidationUtils.validatePhone(state.phoneContact)
//        val addressContactError = ValidationUtils.validateAddress(state.addressContact)
//
//        if (emailError != null || phoneError != null || nameError != null || addressError != null ||
//            nameContactError != null || phoneContactError != null || addressContactError != null
//        ) {
//            state = state.copy(
//                emailError = emailError,
//                phoneError = phoneError,
//                nameError = nameError,
//                addressError = addressError,
//                nameContactError = nameContactError,
//                phoneContactError = phoneContactError,
//                addressContactError = addressContactError
//            )
//            return
//        }
    }

    private fun sendUIEvent(event: EditProfileUIEvent) {
        viewModelScope.launch {
            _events.send(event)
        }
    }
}