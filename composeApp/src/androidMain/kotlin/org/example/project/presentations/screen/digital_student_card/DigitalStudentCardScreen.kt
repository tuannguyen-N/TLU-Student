package org.example.project.presentations.screen.digital_student_card

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.presentations.screen.digital_student_card.components.DigitalStudentCardContent

@Composable
fun DigitalStudentCardScreen(
    viewModel: DigitalStudentCardViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DigitalStudentCardContent(
        uiState = uiState,
        onCreateQr = viewModel::onCreateQr,
        onBackToFrontCard = viewModel::onBackToFrontCard,
        onRegenerateQr = viewModel::onRecreateQr,
        onBack = onBack
    )
}