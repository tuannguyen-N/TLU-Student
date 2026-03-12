package org.example.project.presentations.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.R
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun AppTopBar(
    onOpenNotificationScreen: () -> Unit,
    iconRes: Int,
    title: String
) {
    Row(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .padding(start = 20.dp, top = 10.dp, end =10.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = LocalExtendedColors.current.mainBlue,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = title,
            color = LocalExtendedColors.current.mainBlue,
            modifier = Modifier
                .padding(start = 10.dp)
                .weight(1f),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )

        IconButton(
            onClick = onOpenNotificationScreen
        ) {
            Image(
                painter = painterResource(R.drawable.icon_notification_top_bar),
                contentDescription = null,
            )
        }
    }
}