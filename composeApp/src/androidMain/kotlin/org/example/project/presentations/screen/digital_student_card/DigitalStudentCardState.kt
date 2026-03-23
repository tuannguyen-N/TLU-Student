package org.example.project.presentations.screen.digital_student_card

import org.example.project.data.remote.dto.me.StudentInformation
import org.example.project.domain.model.QrState

data class DigitalStudentCardState(
    val studentInfo: StudentInformation? = null,
    val qrState: QrState = QrState.Idle,

    val isLoading: Boolean = false
) {
    val isFlipped: Boolean get() = qrState !is QrState.Idle
}