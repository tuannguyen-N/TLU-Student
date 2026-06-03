package org.example.project.presentations.screen.message.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.example.project.presentations.screen.message.MessageState
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun MessageInputBar(
    state: MessageState,
    onMessageChange: (String) -> Unit,
    onSend: () -> Unit,
    onImagePick: (Uri) -> Unit = {},
    onRemoveImage: () -> Unit = {}
) {
    val color = LocalExtendedColors.current
    val hasText = state.message.isNotBlank()
    val hasImage = state.selectedImageUri != null

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let { onImagePick(it) }
    }

    Surface(
        tonalElevation = 2.dp,
        color = Color.White,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            AnimatedVisibility(
                visible = hasImage,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Box(
                    modifier = Modifier
                        .padding(start = 12.dp, top = 8.dp, bottom = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFE0E0E0))
                    ) {
                        AsyncImage(
                            model = state.selectedImageUri,
                            contentDescription = "Selected image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.TopStart)
                            .offset(x = (-6).dp, y = (-6).dp)
                            .clip(CircleShape)
                            .background(Color(0xFF333333))
                            .clickable { onRemoveImage() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remove",
                            tint = Color.White,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = state.message,
                    onValueChange = { onMessageChange(it) },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    placeholder = {
                        Text(
                            "Nhập tin nhắn...",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color(0xFF8E8E93)
                            )
                        )
                    },
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF1A73E8),
                        unfocusedBorderColor = Color(0xFFE0E0E0),
                        focusedContainerColor = Color(0xFFF5F5F5),
                        unfocusedContainerColor = Color(0xFFF5F5F5)
                    ),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    maxLines = 4,
                    singleLine = false
                )

                Spacer(modifier = Modifier.width(6.dp))

                AnimatedContent(
                    targetState = hasText || hasImage,
                    transitionSpec = {
                        (scaleIn(initialScale = 0.8f) + fadeIn()) togetherWith
                                (scaleOut(targetScale = 0.8f) + fadeOut())
                    },
                    label = "InputActions"
                ) { showSend ->
                    if (showSend) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF1A73E8))
                                .clickable { onSend() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Send",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    } else {
                        Row {
                            IconButton(
                                onClick = {},
                                modifier = Modifier.size(38.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add",
                                    tint = color.midBlue
                                )
                            }
                            IconButton(
                                onClick = {
                                    imagePickerLauncher.launch(
                                        PickVisualMediaRequest(
                                            ActivityResultContracts.PickVisualMedia.ImageOnly
                                        )
                                    )
                                },
                                modifier = Modifier.size(38.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Image,
                                    contentDescription = "Image",
                                    tint = color.midBlue
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}