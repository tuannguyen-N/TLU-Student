package org.example.project.presentations.screen.class_signed_up

sealed interface SignedUpEvent {
    class CancelClassSuccess(val className: String) : SignedUpEvent
    class CancelClassFailure(val message: String) : SignedUpEvent
}
