package org.example.project.domain.model

data class FeedBackState(
    val title: String = "",
    val subject: SubjectOption? = null,
    val content: String = "",
    val attachedImages: List<String> = emptyList(), // TODO: change list uri
    val selectedTab: Int = 0,
    val subjectExpanded: Boolean = false
) {
    val isFormValid: Boolean
        get() = title.isNotBlank() && subject != null && content.isNotBlank()
}