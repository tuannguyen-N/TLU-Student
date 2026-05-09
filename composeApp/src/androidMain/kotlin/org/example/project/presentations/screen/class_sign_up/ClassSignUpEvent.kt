package org.example.project.presentations.screen.class_sign_up

sealed interface ClassSignUpEvent {
    class EnrollClassSuccess(val courseClassName: String) : ClassSignUpEvent
    class EnrollClassFailure(val message: String) : ClassSignUpEvent
}