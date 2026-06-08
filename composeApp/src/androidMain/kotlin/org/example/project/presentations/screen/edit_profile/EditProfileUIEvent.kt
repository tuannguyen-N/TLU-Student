package org.example.project.presentations.screen.edit_profile

sealed interface EditProfileUIEvent {
    object OnNavigateBack : EditProfileUIEvent
    data class OnSubmitSuccess(val message: String) : EditProfileUIEvent
    data class OnSubmitFailure(val message: String) : EditProfileUIEvent
}