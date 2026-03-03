package org.example.project.presentations.screen.notification.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import org.example.project.R

@Preview
@Composable
fun CustomOverflowMenu(
    onDismiss: () -> Unit = {},
    onMarkAllRead: () -> Unit = {}
) {
    Popup(
        alignment = Alignment.TopEnd,
        offset = IntOffset(x = -20, y = 20),
        onDismissRequest = onDismiss
    ) {
        Box(
            modifier = Modifier
                .width(220.dp)
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(5.dp)
                )
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(5.dp)
                )
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {}
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onMarkAllRead() }
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painter = painterResource(R.drawable.icon_eye),
                    contentDescription = null,
                    tint = Color.Black,
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "Đánh dấu tất cả là đã đọc",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}