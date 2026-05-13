package org.example.project.presentations.screen.application_detail

import org.example.project.data.remote.dto.application_detail.ApplicationDetailData

data class ApplicationDetailState(
    val isLoading: Boolean = true,
    val data: ApplicationDetailData? = null,
    val error: String? = null
)
