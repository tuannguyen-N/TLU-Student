package org.example.project.presentations.screen.digital_student_card.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.project.presentations.components.TopCenterScreenBar
import org.example.project.presentations.screen.digital_student_card.DigitalStudentCardState
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun DigitalStudentCardContent(
    uiState: DigitalStudentCardState,
    onBack: () -> Unit,
    onCreateQr: () -> Unit,
    onRegenerateQr: () -> Unit,
    onBackToFrontCard: () -> Unit
) {
    val color = LocalExtendedColors.current

    Scaffold(
        containerColor = color.background,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            TopCenterScreenBar(
                title = "Thẻ sinh viên điện tử",
                backgroundColor = color.white,
                contentColor = Color.Black,
                onBack = onBack
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 45.dp),
        ) {
            Spacer(Modifier.height(24.dp))

            DigitalStudentCard(
                studentInfo = uiState.studentInfo ?: return@Column,
                qrState = uiState.qrState,
                avatarUrl = uiState.imageBase64,
                onCreateQr = onCreateQr,
                onRegenerateQr = onRegenerateQr,
                onBackToFrontCard = onBackToFrontCard
            )

            Spacer(Modifier.height(24.dp))

            CardStatus()
        }
    }
}