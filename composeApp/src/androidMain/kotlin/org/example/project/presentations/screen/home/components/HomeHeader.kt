package org.example.project.presentations.screen.home.components

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.R
import org.example.project.presentations.theme.LocalExtendedColors

@Preview
@Composable
fun HomeHeader(
    onOpenProfile: () -> Unit = {},
    onOpenNotification: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(LocalExtendedColors.current.mainRed)
    ) {
        Row(
            modifier = Modifier
                .statusBarsPadding()
                .padding(start = 24.dp, end = 15.dp)
                .padding(vertical = 10.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "avatar",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(
                        BorderStroke(1.dp, Color.White),
                        CircleShape
                    )
                    .align(Alignment.CenterVertically)
                    .clickable {
                        onOpenProfile()
                    }
            )

            Column(
                modifier = Modifier
                    .align(
                        Alignment.CenterVertically
                    )
                    .weight(1f)
                    .width(0.dp)
                    .padding(horizontal = 10.dp)
            ) {
                Text(
                    text = "Nguyen Vanh",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "MSV A45044",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal
                )
            }

            IconButton(
                onClick = onOpenNotification,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                Image(
                    painter = painterResource(R.drawable.icon_notification),
                    contentDescription = "notification",
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        }
    }
}