package org.example.project.presentations.screen.message.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.presentations.theme.LocalExtendedColors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageTopBar(onBack: () -> Unit = {}) {
    val color = LocalExtendedColors.current
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = color.midBlue
                )
            }
        },
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(color.gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "N",
                        style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Nguyễn Văn A",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                        )
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(color.green)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Đang hoạt động",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = color.gray
                            )
                        )
                    }
                }
            }
        }
    )
}