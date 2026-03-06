package org.example.project.presentations.screen.edit_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.model.EditProfileState
import org.example.project.domain.usecase.StudentUseCase
import org.example.project.presentations.utils.ValidationUtils

class EditProfileViewModel(
    private val studentUseCase: StudentUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditProfileState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<EditProfileUIEvent>()
    val events = _events.receiveAsFlow()

    private var originalState: EditProfileState = _uiState.value

    val isChanged: Boolean
        get() = _uiState.value != originalState

    val isButtonEnabled: Boolean = with(_uiState.value) {
        emailError == null && phoneError == null && addressError == null && nameContactError == null && phoneContactError == null && addressContactError == null && isChanged
    }

    init {
        observeStudentInfo()
    }

    private fun observeStudentInfo() {
        viewModelScope.launch {
            studentUseCase.studentInfo.collect { studentInformation ->
                studentInformation?.let {
                    updateState {
                        copy(
                            email = email,
                            phone = phone,
                            address = address,
                            nameContact = nameContact,
                            phoneContact = phoneContact,
                            addressContact = addressContact
                        )
                    }
                }
            }
        }
    }

    fun onEmailChange(value: String) {
        updateState {
            copy(
                email = value,
                emailError = ValidationUtils.validateEmail(value)
            )
        }
    }

    fun onPhoneChange(value: String) {
        updateState {
            copy(
                phone = value,
                phoneError = ValidationUtils.validatePhone(value)
            )
        }
    }

    fun onNameContactChange(value: String) {
        updateState {
            copy(
                nameContact = value,
                nameContactError = ValidationUtils.validateRequired(value, "Họ tên người liên hệ")
            )
        }
    }

    fun onPhoneContactChange(value: String) {
        updateState {
            copy(
                phoneContact = value,
                phoneContactError = ValidationUtils.validatePhone(value)
            )
        }
    }

    fun onAddressContactChange(value: String) {
        updateState {
            copy(
                addressContact = value,
                addressContactError = ValidationUtils.validateRequired(
                    value,
                    "Địa chỉ người liên hệ"
                )
            )
        }
    }

    fun onAddressChange(value: String) {
        updateState {
            copy(
                address = value,
                addressError = ValidationUtils.validateRequired(value, "Địa chỉ")
            )
        }
    }

    fun onCancelEditProfile() {
        if (isChanged) {
            updateState { copy(isShowExitDialog = true) }
        } else {
            sendUIEvent(EditProfileUIEvent.OnNavigateBack)
        }
    }

    fun onDismissExitDialog() {
        updateState { copy(isShowExitDialog = false) }
    }

    fun submit() {
    }

    private fun updateState(newState: EditProfileState.() -> EditProfileState) {
        _uiState.update(newState)
    }

    private fun sendUIEvent(event: EditProfileUIEvent) {
        viewModelScope.launch {
            _events.send(event)
        }
    }
}