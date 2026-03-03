package org.example.project.presentations.screen.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.R
import org.example.project.presentations.theme.LocalExtendedColors

@Preview
@Composable
fun SettingScreen(
    onBack: () -> Unit = {}
) {
    Scaffold(
        containerColor = LocalExtendedColors.current.background,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            Box(
                modifier = Modifier.statusBarsPadding(),
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
                    text = "Cài đặt",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 15.dp, start = 25.dp, end = 25.dp)
                    .clip(RoundedCornerShape(13.dp))
                    .background(LocalExtendedColors.current.white)
            ) {
                NotificationSetting()
                NotificationSetting()
                NotificationSetting()
                NotificationSetting(true)
            }

            Row(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.icon_logout),
                    contentDescription = null
                )

                Text(
                    text = "Đăng xuất",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Normal,
                    color = LocalExtendedColors.current.red,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }
    }
}

@Composable
fun NotificationSetting(
    isLastItem: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(15.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.icon_notification_setting),
                contentDescription = null,
            )

            Column(modifier = Modifier.padding(start = 10.dp)) {
                Text(
                    text = "Thông báo",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "Tắt/bật thông báo",
                    style = MaterialTheme.typography.bodySmall,
                    color = LocalExtendedColors.current.gray
                )
            }
        }

        if (!isLastItem) {
            Spacer(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .background(LocalExtendedColors.current.gray)
                    .fillMaxWidth()
                    .height(1.dp)
            )
        }
    }
}