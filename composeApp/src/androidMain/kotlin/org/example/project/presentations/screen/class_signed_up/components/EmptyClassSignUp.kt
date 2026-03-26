package org.example.project.presentations.screen.class_signed_up.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.R
import org.example.project.presentations.theme.ExtendedColors
import org.example.project.presentations.theme.LocalExtendedColors

@Preview(showBackground = true)
@Composable
fun EmptyClassSignUp(
    modifier: Modifier = Modifier,
    color: ExtendedColors = LocalExtendedColors.current
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.icon_empty_class),
            contentDescription = null,
            modifier = Modifier.size(60.dp),
            tint = color.mainBlue
        )
        Spacer(Modifier.height(8.dp))

        Text(
            text = "Chưa có môn học đăng ký",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )

        Text(
            text = "Bạn chưa chọn môn học nào trong học kỳ này. Hãy quay lại danh sách đăng ký để chọn các môn học phù hợp",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
        Spacer(Modifier.height(20.dp))
    }
}