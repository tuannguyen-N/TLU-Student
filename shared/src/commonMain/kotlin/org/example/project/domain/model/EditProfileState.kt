package org.example.project.domain.model

data class EditProfileState(
    val email: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val nameContact: String? = null,
    val phoneContact: String? = null,
    val addressContact: String? = null,

    val emailError: String? = null,
    val phoneError: String? = null,
    val addressError: String? = null,
    val nameContactError: String? = null,
    val phoneContactError: String? = null,
    val addressContactError: String? = null,

    val isLoading: Boolean = false,
    val isShowExitDialog: Boolean = false
)