package org.example.project.presentations.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import org.example.project.R
import org.example.project.presentations.theme.LocalExtendedColors

@Preview(showBackground = true)
@Composable
fun AppTopBar(
    onOpenNotificationScreen: () -> Unit = {},
    iconRes: Int = R.drawable.icon_school_schedule,
    title: String = "Lịch học",
    backgroundColor: Color = Color.Transparent,
    isNotificationBadgeVisible: Boolean = false,
    onOpenChat: () -> Unit = {}
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.ai_star)
    )

    Row(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(start = 24.dp, end = 15.dp)
            .padding(vertical = 15.dp),
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
            onClick = onOpenChat,
            modifier = Modifier.size(30.dp)
        ) {
            Box {
                Image(
                    painter = painterResource(R.drawable.icon_chat_ai),
                    contentDescription = null,
                )
                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier
                        .size(40.dp)
                        .offset(0.dp, (-10).dp)
                        .scale(1.2f)
                )
            }
        }

        Spacer(modifier = Modifier.width(5.dp))

        IconButton(
            onClick = onOpenNotificationScreen,
            modifier = Modifier.size(30.dp)
        ) {
            Box {
                Image(
                    painter = painterResource(R.drawable.icon_notification_top_bar),
                    contentDescription = null,
                )

                if(isNotificationBadgeVisible){
                    Spacer(
                        Modifier
                            .padding(5.dp)
                            .size(8.dp)
                            .background(LocalExtendedColors.current.red, shape = CircleShape)
                            .align(Alignment.TopEnd)
                    )
                }
            }
        }
    }
}

