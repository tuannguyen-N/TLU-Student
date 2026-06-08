package org.example.project.presentations.screen.profile.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import androidx.compose.ui.unit.lerp
import coil.compose.AsyncImage
import org.example.project.R
import org.example.project.presentations.theme.LocalExtendedColors

@SuppressLint("RestrictedApi")
@Composable
fun HeaderProfile(
    avatarUrl: String?,
    studentName: String,
    majorName: String,
    progress: Float,
    onClickBack: () -> Unit,
    onClickSetting: () -> Unit
) {
    val height = lerp(160.dp, 70.dp, progress)
    val backgroundHeight = lerp(250.dp, 70.dp, progress)
    val scale = lerp(1f, 0.6f, progress)

    Box(
        modifier = Modifier.height(backgroundHeight)
    ) {
        Image(
            painter = painterResource(R.drawable.image_background_profile),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding(),
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

            if (!avatarUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = avatarUrl,
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        }
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.White, CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        }
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(LocalExtendedColors.current.gray)
                        .border(1.dp, Color.White, CircleShape)
                        .padding(20.dp)
                )
            }

            Text(
                text = studentName,
                fontWeight = FontWeight.Bold,
                color = LocalExtendedColors.current.mainBlue,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(top = 5.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }

            )

            Text(
                text = majorName,
                color = LocalExtendedColors.current.gray,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
            )
        }
    }
}