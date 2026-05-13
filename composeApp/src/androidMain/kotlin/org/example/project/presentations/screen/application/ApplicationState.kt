package org.example.project.presentations.screen.application

import org.example.project.data.remote.dto.application.ApplicationType
import org.example.project.data.remote.dto.application_history.ApplicationHistoryData
import org.example.project.domain.model.SubmitState

data class ApplicationState(
    val applicationTypes: List<ApplicationType> = emptyList(),
    val isLoading: Boolean = false,
    val submitState: SubmitState = SubmitState.Idle,
    val selectedApplicationType: ApplicationType? = null,
    val subjectExpanded: Boolean = false,
    val attachedFile: String? = null,
    val content: String? = null,
    val selectedTab: Int = 0,

    val applicationHistory: List<ApplicationHistoryData> = emptyList()
) {
    val isFormValid: Boolean
        get() = selectedApplicationType != null && attachedFile != null
}