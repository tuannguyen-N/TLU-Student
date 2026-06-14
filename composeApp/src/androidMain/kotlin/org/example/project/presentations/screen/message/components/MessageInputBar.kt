package org.example.project.presentations.screen.message.components

import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
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
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.InsertDriveFile
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.example.project.presentations.screen.message.MessageState
import org.example.project.presentations.theme.LocalExtendedColors
import org.example.project.presentations.utils.buildMentionText

@Composable
fun MessageInputBar(
    modifier: Modifier = Modifier,
    state: MessageState,
    onMessageChange: (TextFieldValue) -> Unit,
    onSend: (fileName: String?, fileSize: String?) -> Unit,
    onImagePick: (Uri) -> Unit = {},
    onRemoveImage: () -> Unit = {},
    onFilePick: (Uri) -> Unit = {},
    onRemoveFile: () -> Unit = {},
    onVideoPick: (Uri) -> Unit = {},
    onRemoveVideo: () -> Unit = {},
) {
    val color = LocalExtendedColors.current
    val context = LocalContext.current
    val hasText = state.message.text.isNotBlank()
    val hasImage = state.selectedImageUri != null
    val hasFile = state.selectedFileUri != null
    val hasVideo = state.selectedVideoUri != null

    val mentionSuggestions = listOf("tlu_ai")

    val mentionQuery = run {
        val lastAt = state.message.text.lastIndexOf('@')
        if (lastAt >= 0) {
            val after = state.message.text.substring(lastAt + 1)
            if (!after.contains(' ')) after else null
        } else {
            null
        }
    }

    val filteredMentions = mentionQuery?.let { query ->
        mentionSuggestions.filter {
            it.startsWith(query, ignoreCase = true)
        }
    } ?: emptyList()

    val showMentionSuggestions = filteredMentions.isNotEmpty()

    LaunchedEffect(state.message) {
        Log.d("MENTION", "message=${state.message}")
        Log.d("MENTION", "query=$mentionQuery")
        Log.d("MENTION", "show=$showMentionSuggestions")
    }

    fun onMentionSelect(mention: String) {
        val lastAt = state.message.text.lastIndexOf('@')
        if (lastAt >= 0) {
            val newText =
                state.message.text.take(lastAt) + "@$mention "

            onMessageChange(
                TextFieldValue(text = newText, selection = TextRange(newText.length))
            )
        }
    }

    val fileName = remember(state.selectedFileUri) {
        state.selectedFileUri?.let { uri ->
            context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                cursor.moveToFirst()
                if (nameIndex >= 0) cursor.getString(nameIndex) else "File"
            } ?: uri.lastPathSegment ?: "File"
        }
    }

    val fileSize = remember(state.selectedFileUri) {
        state.selectedFileUri?.let { uri ->
            context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                cursor.moveToFirst()
                if (sizeIndex >= 0) {
                    val bytes = cursor.getLong(sizeIndex)
                    when {
                        bytes < 1024 -> "${bytes}B"
                        bytes < 1024 * 1024 -> "${bytes / 1024}KB"
                        else -> String.format("%.1fMB", bytes / (1024f * 1024f))
                    }
                } else null
            }
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            val mimeType = context.contentResolver.getType(uri)
            if (mimeType?.startsWith("video/") == true) {
                onVideoPick(it)
            } else {
                onImagePick(it)
            }
        }
    }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> uri?.let { onFilePick(it) } }

    Surface(
        tonalElevation = 2.dp,
        color = Color.White,
        modifier = modifier.fillMaxWidth()
    ) {
        Column {
            AnimatedVisibility(
                visible = showMentionSuggestions,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {

                    filteredMentions.forEach { mention ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFF0F4FF))
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFFD0DCFF),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clickable { onMentionSelect(mention) }
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF1A73E8)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "AI",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                            Column {
                                Text(
                                    text = "@$mention",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFF1A73E8)
                                    )
                                )
                                Text(
                                    text = "TLU AI Assistant",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color(0xFF8E8E93)
                                    )
                                )
                            }
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = hasImage,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Box(
                    modifier = Modifier.padding(start = 12.dp, top = 8.dp, bottom = 4.dp)
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
                            .align(Alignment.TopEnd)
                            .offset(x = 6.dp, y = (-6).dp)
                            .clip(CircleShape)
                            .background(Color(0xFF333333))
                            .clickable { onRemoveImage() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remove image",
                            tint = Color.White,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = hasVideo,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Box(
                    modifier = Modifier.padding(start = 12.dp, top = 8.dp, bottom = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 120.dp, height = 72.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF1A1A1A))
                    ) {
                        val isPreview = LocalInspectionMode.current
                        if (!isPreview) {
                            val ctx = LocalContext.current
                            val thumbnailBitmap = remember(state.selectedVideoUri) {
                                state.selectedVideoUri?.let { uri ->
                                    val retriever = MediaMetadataRetriever()
                                    try {
                                        retriever.setDataSource(ctx, uri)
                                        retriever.getFrameAtTime(0)
                                    } catch (e: Exception) {
                                        null
                                    } finally {
                                        retriever.release()
                                    }
                                }
                            }
                            thumbnailBitmap?.let {
                                Image(
                                    bitmap = it.asImageBitmap(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(Color.Black.copy(alpha = 0.4f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.PlayArrow,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                                    .padding(6.dp)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.TopEnd)
                            .offset(x = 6.dp, y = (-6).dp)
                            .clip(CircleShape)
                            .background(Color(0xFF333333))
                            .clickable { onRemoveVideo() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remove video",
                            tint = Color.White,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = hasFile,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Box(
                    modifier = Modifier.padding(start = 12.dp, top = 8.dp, bottom = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .widthIn(min = 160.dp, max = 260.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFF0F4FF))
                            .border(
                                width = 1.dp,
                                color = Color(0xFFD0DCFF),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF1A73E8)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.InsertDriveFile,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = fileName ?: "File",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF1A1A2E)
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            if (fileSize != null) {
                                Text(
                                    text = fileSize,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color(0xFF8E8E93)
                                    )
                                )
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.TopEnd)
                            .offset(x = 6.dp, y = (-6).dp)
                            .clip(CircleShape)
                            .background(Color(0xFF333333))
                            .clickable { onRemoveFile() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remove file",
                            tint = Color.White,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 4.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = state.message,
                    onValueChange = onMessageChange,
                    modifier = Modifier
                        .weight(1f)
                        .border(
                            width = 1.dp,
                            color = Color(0xFFE0E0E0),
                            shape = RoundedCornerShape(18.dp)
                        )
                        .background(
                            Color(0xFFF5F5F5),
                            RoundedCornerShape(18.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Transparent
                    ),
                    cursorBrush = SolidColor(Color(0xFF1A73E8)),
                    decorationBox = { innerTextField ->
                        Box {
                            if (state.message.text.isEmpty()) {
                                Text(
                                    text = "Nhập tin nhắn...",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = Color(0xFF8E8E93)
                                    )
                                )
                            }
                            Text(
                                text = buildMentionText(state.message.text),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            innerTextField()
                        }
                    }
                )

                Spacer(modifier = Modifier.width(6.dp))

                AnimatedContent(
                    targetState = hasText || hasImage || hasFile || hasVideo,
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
                                .clickable { onSend(fileName, fileSize) },
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
                                onClick = {
                                    imagePickerLauncher.launch(
                                        PickVisualMediaRequest(
                                            ActivityResultContracts.PickVisualMedia.ImageAndVideo
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

                            IconButton(
                                onClick = { filePickerLauncher.launch("*/*") },
                                modifier = Modifier.size(38.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AttachFile,
                                    contentDescription = "File",
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