package org.example.project.presentations.screen.class_sign_up.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SemesterInformation(gray: Color) {
    Spacer(Modifier.height(16.dp))
    Text(
        text = "Học kỳ 1 - 2024",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(Modifier.height(2.dp))
    Text(
        text = "THỜI GIAN ĐĂNG KÝ CÒN LẠI: 4 NGÀY",
        style = MaterialTheme.typography.labelMedium,
        color = gray
    )
}