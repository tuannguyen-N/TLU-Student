package org.example.project.presentations.screen.notification.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.example.project.R

@Preview
@Composable
fun HeaderContainer(
    onBack: () -> Unit = {}
) {
    var showMenu by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBack
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_back),
                contentDescription = null,
                tint = Color.Black
            )
        }

        Text(
            text = "Thông báo",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f),
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )

        Box {
            IconButton(
                onClick = { showMenu = true },
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_more),
                    contentDescription = null,
                    tint = Color.Black
                )
            }

            if (showMenu) {
                CustomOverflowMenu(
                    onDismiss = { showMenu = false },
                    onMarkAllRead = {
                        showMenu = false
                        // TODO:
                    }
                )
            }
        }
    }
}