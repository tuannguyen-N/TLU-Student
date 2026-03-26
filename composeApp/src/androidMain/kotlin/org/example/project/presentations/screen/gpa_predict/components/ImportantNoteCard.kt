package org.example.project.presentations.screen.gpa_predict.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.presentations.theme.LocalExtendedColors

@Preview(showBackground = true)
@Composable
fun ImportantNoteCard(
    modifier: Modifier = Modifier
) {
    val color = LocalExtendedColors.current
    val message = "Kết quả này chỉ mang tính chất tham khảo dựa trên điểm số bạn nhập vào. " +
            "Điểm chính thức sẽ được cập nhật sau khi kết thúc học kỳ từ hệ thống đào tạo."
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = color.lightBlue.copy(alpha = 0.05f)),
        border = BorderStroke(1.dp, color.lightBlue.copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = null,
                    tint = color.lightBlue,
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
                    color = color.lightBlue
                )

                Text(
                    text = message,
                    style = MaterialTheme.typography.bodySmall,
                    color = color.grayNavy,
                    lineHeight = 24.sp
                )
            }
        }
    }
}