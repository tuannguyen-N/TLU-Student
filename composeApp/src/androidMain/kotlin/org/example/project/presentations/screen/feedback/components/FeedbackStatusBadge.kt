package org.example.project.presentations.screen.feedback.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.ApplicationStatus

@Composable
fun FeedbackStatusBadge(status: ApplicationStatus) {
    val (label, backgroundColor, textColor) = when (status) {
        ApplicationStatus.PENDING -> Triple(
            "Đang chờ xử lý",
            Color(0xFFFFF3E0),
            Color(0xFFE65100)
        )

        ApplicationStatus.PROCESSING -> Triple(
            "Đang xử lý",
            Color(0xFFE3F2FD),
            Color(0xFF1565C0)
        )

        ApplicationStatus.RESOLVED -> Triple(
            "Đã xử lý",
            Color(0xFFE8F5E9),
            Color(0xFF2E7D32)
        )

        ApplicationStatus.APPROVED -> Triple(
            "Đã phê duyệt",
            Color(0xFFE8F5E9),
            Color(0xFF2E7D32)
        )
    }

    Box(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = label,
            color = textColor,
            style = MaterialTheme.typography.labelSmall,
        )
    }
}