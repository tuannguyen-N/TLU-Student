package org.example.project.presentations.screen.tuition_payment

import org.example.project.data.remote.dto.tuition_detail.TuitionItem

sealed interface TuitionUiEvent {
    data class ShowDetailTuitionCourseDialog(val courses: List<TuitionItem>) : TuitionUiEvent
}