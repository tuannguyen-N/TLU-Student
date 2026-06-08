package org.example.project.presentations.screen.edit_profile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.data.remote.dto.me.SelfUpdateRequest
import org.example.project.data.remote.interceptor.AuthPluginConfig
import org.example.project.domain.model.AppResult
import org.example.project.domain.usecase.StudentUseCase
import org.example.project.presentations.utils.ValidationUtils

class EditProfileViewModel(
    private val studentUseCase: StudentUseCase,
    private val authPluginConfig: AuthPluginConfig
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditProfileState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<EditProfileUIEvent>()
    val events = _events.receiveAsFlow()

    private var originalState: EditProfileState = _uiState.value

    val isChanged: Boolean
        get() {
            val s = _uiState.value
            return s.email != originalState.email ||
                    s.phone != originalState.phone ||
                    s.address != originalState.address ||
                    s.nameContact != originalState.nameContact ||
                    s.phoneContact != originalState.phoneContact ||
                    s.addressContact != originalState.addressContact ||
                    s.selectedImageBytes != null
        }

    val isButtonEnabled: Boolean
        get() {
            val s = _uiState.value
            return s.emailError == null &&
                    s.phoneError == null &&
                    s.addressError == null &&
                    s.nameContactError == null &&
                    s.phoneContactError == null &&
                    s.addressContactError == null &&
                    !s.isLoading &&
                    isChanged
        }

    init {
        observeStudentInfo()
    }

    private fun observeStudentInfo() {
        viewModelScope.launch {
            studentUseCase.studentInfo.collect { studentData ->
                studentData?.let { data ->
                    val initialState = EditProfileState(
                        email = data.contact.email,
                        phone = data.contact.phoneNumber,
                        address = data.contact.address,
                        nameContact = data.emergencyContact.name,
                        phoneContact = data.emergencyContact.phoneNumber,
                        addressContact = data.emergencyContact.address,
                        avatarUrl = data.avatarUrl
                    )
                    _uiState.update { initialState }
                    originalState = initialState
                }
            }
        }
    }

    fun onImageSelected(uri: Uri, context: Context) {
        updateState { copy(selectedImageUri = uri, selectedImageBytes = null) }
        viewModelScope.launch(Dispatchers.IO) {
            val bytes = context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
            if (bytes != null) {
                updateState { copy(selectedImageBytes = bytes) }
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
        if (!isButtonEnabled) return
        viewModelScope.launch {
            updateState { copy(isLoading = true) }

            val state = _uiState.value
            var hasError = false
            var errorMessage = "Cập nhật thất bại"

            // 1. Update info fields
            val infoChanged = state.email != originalState.email ||
                    state.phone != originalState.phone ||
                    state.address != originalState.address ||
                    state.nameContact != originalState.nameContact ||
                    state.phoneContact != originalState.phoneContact ||
                    state.addressContact != originalState.addressContact

            if (infoChanged) {
                val result = studentUseCase.updateStudentInfo(
                    SelfUpdateRequest(
                        phoneNumber = state.phone,
                        address = state.address,
                        email = state.email,
                        emergencyContactName = state.nameContact,
                        emergencyContactPhoneNumber = state.phoneContact,
                        emergencyContactAddress = state.addressContact
                    )
                )
                if (result is AppResult.Failure) {
                    hasError = true
                    errorMessage = result.message ?: "Cập nhật thông tin thất bại"
                }
            }

            // 2. Upload avatar (only if a new image was selected)
            val imageBytes = state.selectedImageBytes
            val imageUri = state.selectedImageUri
            if (!hasError && imageBytes != null) {
                val fileName = imageUri?.lastPathSegment ?: "avatar.jpg"
                val result = studentUseCase.updateAvatar(
                    fileName = fileName,
                    fileBytes = imageBytes
                )
                if (result is AppResult.Failure) {
                    hasError = true
                    errorMessage = result.message ?: "Cập nhật ảnh thất bại"
                }
            }

            updateState { copy(isLoading = false) }

            if (hasError) {
                sendUIEvent(EditProfileUIEvent.OnSubmitFailure(errorMessage))
            } else {
                studentUseCase.getStudentInfo()
                sendUIEvent(EditProfileUIEvent.OnSubmitSuccess("Cập nhật thông tin thành công"))
            }
        }
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