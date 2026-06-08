package org.example.project.presentations.screen.edit_profile

import android.net.Uri

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

    val avatarUrl: String? = null,

    // Newly selected image (not yet submitted)
    val selectedImageUri: Uri? = null,
    val selectedImageBytes: ByteArray? = null,

    val isLoading: Boolean = false,
    val isShowExitDialog: Boolean = false,

    val successMessage: String? = null,
    val failureMessage: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EditProfileState) return false
        return email == other.email &&
            phone == other.phone &&
            address == other.address &&
            nameContact == other.nameContact &&
            phoneContact == other.phoneContact &&
            addressContact == other.addressContact &&
            emailError == other.emailError &&
            phoneError == other.phoneError &&
            addressError == other.addressError &&
            nameContactError == other.nameContactError &&
            phoneContactError == other.phoneContactError &&
            addressContactError == other.addressContactError &&
            avatarUrl == other.avatarUrl &&
            selectedImageUri == other.selectedImageUri &&
            selectedImageBytes.contentEquals(other.selectedImageBytes) &&
            isLoading == other.isLoading &&
            isShowExitDialog == other.isShowExitDialog &&
            successMessage == other.successMessage &&
            failureMessage == other.failureMessage
    }

    override fun hashCode(): Int {
        var result = email.hashCode()
        result = 31 * result + phone.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + nameContact.hashCode()
        result = 31 * result + phoneContact.hashCode()
        result = 31 * result + addressContact.hashCode()
        result = 31 * result + (selectedImageBytes?.contentHashCode() ?: 0)
        return result
    }
}

private fun ByteArray?.contentEquals(other: ByteArray?): Boolean {
    if (this == null && other == null) return true
    if (this == null || other == null) return false
    return java.util.Arrays.equals(this, other)
}