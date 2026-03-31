package org.example.project.presentations.screen.digital_student_card.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import org.example.project.data.remote.dto.me.StudentData
import org.example.project.domain.model.QrState

@Composable
fun DigitalStudentCard(
    modifier: Modifier = Modifier,
    studentInfo: StudentData,
    qrState: QrState,
    onCreateQr: () -> Unit,
    onRegenerateQr: () -> Unit,
    onBackToFrontCard: () -> Unit
) {
    val isFlipped = qrState != QrState.Idle
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(500), label = ""
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12 * density
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Box {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(if (rotation <= 90f) 1f else 0f)
            ) {
                FrontCard(
                    name = studentInfo.fullName,
                    studentCode = studentInfo.studentCode,
                    faculty = studentInfo.major.majorName,
                    birthDate = studentInfo.dateOfBirth,
                    course = "", // TODO:
                    avatarUrl = "",
                    onCreateQr = onCreateQr
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        rotationY = 180f
                    }
                    .alpha(if (rotation > 90f) 1f else 0f)
                    .align(Alignment.Center),
            ) {
                when (qrState) {
//                    is QrState.Generating -> QrLoadingContent()
                    is QrState.Active -> BackCardQR(
                        name = studentInfo.fullName,
                        timeLeft = qrState.timeLeft,
                        qrBitmap = qrState.qrBitmap,
                        onBack = onBackToFrontCard,
                        onRegenerateQr = onRegenerateQr
                    )

                    is QrState.Expired -> BackCardQR(
                        name = studentInfo.fullName,
                        timeLeft = 0,
                        onBack = onBackToFrontCard,
                        onRegenerateQr = onRegenerateQr
                    )

                    else -> Unit
                }
            }
        }
    }
}