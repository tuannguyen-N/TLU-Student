package org.example.project.presentations.screen.application

import org.example.project.data.remote.dto.application.ApplicationType

data class ApplicationState(
    val applicationTypes: List<ApplicationType> = emptyList(),
    val isLoading: Boolean = false,
    val selectedApplicationType: ApplicationType? = null,
    val subjectExpanded: Boolean = false,
    val attachedFile: String? = null
) {
    val isFormValid: Boolean
        get() = selectedApplicationType != null && attachedFile != null
}