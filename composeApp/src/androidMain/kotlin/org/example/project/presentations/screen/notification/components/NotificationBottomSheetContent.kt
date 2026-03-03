package org.example.project.presentations.screen.notification.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.R
import org.example.project.presentations.theme.LocalExtendedColors

@Preview
@Composable
fun NotificationBottomSheetContent(
    onDismiss: () -> Unit = {},
    onUnseenNotification: () -> Unit = {},
    onDeleteNotification: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxWidth().background(Color.White).padding(top = 5.dp)
    ) {

        ActionItem(
            iconRes = R.drawable.icon_unseen,
            title = "Đánh dấu là chưa xem",
            color = Color.Black,
            onClickAction = {
                onUnseenNotification()
                onDismiss()
            }
        )

        ActionItem(
            iconRes = R.drawable.icon_unseen,
            title = "Xoá thông báo",
            color = LocalExtendedColors.current.red,
            onClickAction = {
                onDeleteNotification()
                onDismiss()
            }
        )
    }
}

@Composable
fun ActionItem(
    iconRes: Int,
    color: Color,
    title: String,
    onClickAction: () -> Unit
){
    Surface(
        onClick = onClickAction,
        color = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 15.dp)
        ){
            Icon(
                painter = painterResource(iconRes),
                contentDescription =  null,
                tint = color,
            )

            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = color,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}