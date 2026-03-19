package org.example.project.presentations.screen.gpa_predict.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import org.example.project.presentations.theme.LocalExtendedColors

@Preview
@Composable
fun PopupAlertGpa(
    onDismiss: () -> Unit = {}
) {
    Popup(
        alignment = Alignment.TopStart,
        onDismissRequest = onDismiss,
        offset = IntOffset(x = -20, y = 20)
    ) {
        Box(
            modifier = Modifier
                .width(230.dp)
                .shadow(1.dp, RoundedCornerShape(8.dp))
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = "Dưới 4.0: Không tính tích lũy & GPA.",
                color = LocalExtendedColors.current.red,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}