package org.example.project.presentations.screen.feedback.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.presentations.theme.LocalExtendedColors

@Preview
@Composable
fun ImportantNoteFeedbackCard(
    modifier: Modifier = Modifier
) {
    val color = LocalExtendedColors.current
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = color.fontBlue.copy(alpha = 0.05f)),
        border = BorderStroke(1.dp, color.fontBlue.copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = null,
                    tint = color.mainBlue,
                    modifier = Modifier.size(16.dp)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "LƯU Ý QUAN TRỌNG",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    ),
                    color = color.mainBlue
                )

                DotText(text = "Yêu cầu sẽ được xử lý trong vòng 3-5 ngày làm việc")
                DotText(text = "Bạn sẽ nhận được thông báo khi yêu cầu được duyệt")
                DotText(text = "Đảm bảo thông tin chính xác để tránh bị từ chối")
            }
        }
    }
}

@Composable
private fun DotText(
    text: String
) {
    val color = LocalExtendedColors.current
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier
            .size(4.dp)
            .clip(CircleShape)
            .background(color.grayNavy))
        Spacer(Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = color.grayNavy
        )
    }
}