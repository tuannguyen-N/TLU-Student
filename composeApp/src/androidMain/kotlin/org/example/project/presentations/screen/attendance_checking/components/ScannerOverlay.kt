package org.example.project.presentations.screen.attendance_checking.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun ScannerOverlay() {
    val infiniteTransition = rememberInfiniteTransition(label = "laser")
    val laserY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "laserY"
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val rectSize = 260.dp.toPx()
            val left = (size.width - rectSize) / 2
            val top = (size.height - rectSize) / 2
            val rect = Rect(left, top, left + rectSize, top + rectSize)

            clipPath(Path().apply {
                addRoundRect(
                    RoundRect(
                        rect
                    )
                )
            }, clipOp = ClipOp.Difference) {
                drawRect(color = Color.Black.copy(alpha = 0.6f))
            }

            val strokeWidth = 4.dp.toPx()
            val cornerLength = 24.dp.toPx()
            val cornerColor = Color(0xFF1D9E75)

            drawPath(
                path = Path().apply {
                    moveTo(left, top + cornerLength)
                    lineTo(left, top)
                    lineTo(left + cornerLength, top)
                },
                color = cornerColor,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            drawPath(
                path = Path().apply {
                    moveTo(left + rectSize - cornerLength, top)
                    lineTo(left + rectSize, top)
                    lineTo(left + rectSize, top + cornerLength)
                },
                color = cornerColor,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            drawPath(
                path = Path().apply {
                    moveTo(left, top + rectSize - cornerLength)
                    lineTo(left, top + rectSize)
                    lineTo(left + cornerLength, top + rectSize)
                },
                color = cornerColor,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            drawPath(
                path = Path().apply {
                    moveTo(left + rectSize - cornerLength, top + rectSize)
                    lineTo(left + rectSize, top + rectSize)
                    lineTo(left + rectSize, top + rectSize - cornerLength)
                },
                color = cornerColor,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            drawLine(
                color = cornerColor.copy(alpha = 0.8f),
                start = Offset(left + 8.dp.toPx(), top + rectSize * laserY),
                end = Offset(left + rectSize - 8.dp.toPx(), top + rectSize * laserY),
                strokeWidth = 2.dp.toPx()
            )
        }

        Text(
            text = "Đặt mã QR vào giữa khung hình để quét",
            color = Color.White,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 340.dp)
                .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}