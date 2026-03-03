package org.example.project.domain.model

data class EditProfileState(
    val email: String = "",
    val emailError: String? = null,
    val phone: String = "",
    val phoneError: String? = null,
    val address: String = "",
    val addressError: String? = null,

    val nameContact: String = "",
    val nameContactError: String? = null,
    val phoneContact: String = "",
    val phoneContactError: String? = null,
    val addressContact: String = "",
    val addressContactError: String? = null,

    val isLoading: Boolean = false,
    val isShowExitDialog: Boolean = false
)