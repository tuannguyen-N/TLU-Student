package org.example.project.presentations.screen.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.R
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun HeaderProfile(
    studentName: String,
    majorName: String,
    onClickBack: () -> Unit,
    onClickSetting: () -> Unit
) {
    Box {
        Image(
            painter = painterResource(R.drawable.image_background_profile),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    ,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onClickBack) {
                    Icon(
                        painter = painterResource(R.drawable.icon_back),
                        tint = LocalExtendedColors.current.white,
                        contentDescription = null
                    )
                }

                IconButton(onClick = onClickSetting) {
                    Icon(
                        painter = painterResource(R.drawable.icon_setting),
                        tint = LocalExtendedColors.current.white,
                        contentDescription = null
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.White, CircleShape)
            )

            Text(
                text = studentName,
                fontWeight = FontWeight.Bold,
                color = LocalExtendedColors.current.mainBlue,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 5.dp)
            )

            Text(
                text = majorName,
                color = LocalExtendedColors.current.gray,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}