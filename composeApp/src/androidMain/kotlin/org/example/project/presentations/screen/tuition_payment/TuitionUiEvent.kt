package org.example.project.presentations.screen.tuition_payment

sealed interface TuitionUiEvent {
    object ShowDialogPaymentSuccess: TuitionUiEvent
    object ShowDialogPaymentFailed: TuitionUiEvent
}