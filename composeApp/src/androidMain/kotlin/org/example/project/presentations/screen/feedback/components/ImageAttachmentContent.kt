package org.example.project.presentations.screen.feedback.components

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.FeedBackState
import org.example.project.presentations.theme.LocalExtendedColors
import androidx.core.net.toUri
import coil.compose.AsyncImage

@Composable
fun ImageAttachmentContent(
    uiState: FeedBackState,
    onRemoveImage: (Uri) -> Unit,
    onOpenImagePicker: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.padding(top = 15.dp)
    ) {
        Text(
            text = "Đính kèm hình ảnh",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )
        Text(
            text = "Tối đa 5 hình ảnh (${uiState.attachedImages.size}/5)",
            style = MaterialTheme.typography.bodySmall,
        )

        LazyRow(
            modifier = Modifier.padding(top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.attachedImages) { imageUriString ->
                val uri = imageUriString.toUri()
                Box(modifier = Modifier.size(85.dp)) {
                    AsyncImage(
                        model = uri,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(85.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Surface(
                        onClick = { onRemoveImage(uri) },
                        shape = CircleShape,
                        color = Color.Black.copy(alpha = 0.6f),
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                            .size(20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Xóa ảnh",
                            tint = Color.White,
                            modifier = Modifier.padding(3.dp)
                        )
                    }
                }
            }

            if (uiState.attachedImages.size < 5) {
                item {
                    Surface(
                        onClick = onOpenImagePicker,
                        color = Color.Transparent,
                        modifier = Modifier
                            .size(85.dp)
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .drawBehind {
                                    val strokeWidth = 2.dp.toPx()
                                    val cornerRadius = 8.dp.toPx()
                                    drawRoundRect(
                                        color = Color.Gray,
                                        size = size,
                                        cornerRadius = CornerRadius(cornerRadius),
                                        style = Stroke(
                                            width = strokeWidth,
                                            pathEffect = PathEffect.dashPathEffect(
                                                floatArrayOf(8f, 6f), 0f
                                            )
                                        )
                                    )
                                }
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Thêm hình ảnh",
                                    tint = LocalExtendedColors.current.gray,
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(
                                    text = "Thêm hình ảnh",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = LocalExtendedColors.current.gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}