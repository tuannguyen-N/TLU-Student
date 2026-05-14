package org.example.project.presentations.screen.feedback

import org.example.project.data.remote.dto.feedback.FeedbackCategoryData
import org.example.project.domain.model.SubmitState

data class FeedBackState(
    val title: String = "",
    val feedbackCategory: FeedbackCategoryData? = null,
    val feedbackCategories: List<FeedbackCategoryData> = emptyList(),
    val content: String = "",
    val attachedImages: List<String> = emptyList(), // TODO: change list uri
    val subjectExpanded: Boolean = false,
    val submitState: SubmitState = SubmitState.Idle
) {
    val isFormValid: Boolean
        get() = title.isNotBlank() && feedbackCategory != null && content.isNotBlank()
}