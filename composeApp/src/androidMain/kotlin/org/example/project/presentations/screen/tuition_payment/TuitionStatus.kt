package org.example.project.presentations.screen.tuition_payment

import org.example.project.domain.model.TuitionDetailUiModel
import org.example.project.domain.model.TuitionUiModel

data class TuitionStatus(
    val allTuition: List<TuitionUiModel>? = null,
    val currentTuitionDetail: TuitionDetailUiModel? = null,

    val selectedTab: Int = 0,
    val isLoading: Boolean = false
)
