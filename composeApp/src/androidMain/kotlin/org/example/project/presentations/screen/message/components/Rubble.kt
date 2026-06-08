package org.example.project.presentations.screen.message.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.InsertDriveFile
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.IncompleteCircle
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.example.project.domain.model.MessageStatus
import org.example.project.domain.model.MessageType
import org.example.project.domain.model.MessageUiState
import org.example.project.domain.utils.DateTimeUtils
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun FileBubble(
    message: MessageUiState,
    avatarUrl: String?,
    isMe: Boolean,
    modifier: Modifier = Modifier
) {
    val color = LocalExtendedColors.current

    val bubbleColor = if (isMe) color.midBlue else Color(0xFFE5E5E5)
    val textColor = if (isMe) Color.White else color.blackBackground
    val iconBgColor = if (isMe) color.lightBlue else Color(0xFFD0D0D0)
    val subTextColor =
        if (isMe) Color.White.copy(alpha = 0.75f) else color.blackBackground.copy(alpha = 0.55f)

    Column(
        modifier = modifier
            .background(bubbleColor, RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(iconBgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.InsertDriveFile,
                    contentDescription = null,
                    tint = if (isMe) Color.White else color.blackBackground,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = message.fileName.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = textColor,
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    text = message.fileSize.orEmpty(),
                    style = MaterialTheme.typography.bodySmall.copy(color = subTextColor)
                )
            }
        }

        if (!message.text.isNullOrBlank()) {
            Text(
                text = message.text!!,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = textColor,
                    lineHeight = 22.sp
                ),
                modifier = Modifier.padding(
                    start = 14.dp, end = 14.dp,
                    top = 0.dp
                )
            )
        }
    }
}

@Composable
fun MessageBubble(
    message: MessageUiState,
    showTime: Boolean,
    isLast: Boolean,
    onClick: () -> Unit,
    onClickImage: (url: String) -> Unit,
    onClickFile: (String) -> Unit,
    avatarUrl: String?,
    chatUserName: String
) {
    val isMe = message.isMe
    val color = LocalExtendedColors.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isMe) Alignment.End else Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start,
                verticalAlignment = Alignment.Bottom
            ) {
                if (!isMe) {
                    if (!avatarUrl.isNullOrEmpty()) {
                        AsyncImage(
                            model = avatarUrl,
                            contentDescription = "Avatar",
                            modifier = Modifier
                                .padding(end = 6.dp)
                                .size(32.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .padding(end = 6.dp)
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFBDBDBD)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = chatUserName.substringAfterLast(" ").firstOrNull()
                                    ?.uppercase() ?: "N",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = Color.White
                                )
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .widthIn(max = 280.dp)
                        .padding(end = if (isMe && !isLast) 18.dp else 0.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 18.dp,
                                topEnd = 18.dp,
                                bottomStart = if (isMe) 18.dp else 4.dp,
                                bottomEnd = if (isMe) 4.dp else 18.dp
                            )
                        )
                        .background(if (isMe) color.midBlue else Color(0xFFE5E5E5))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                when (message.type) {
                                    MessageType.IMAGE.name -> {
                                        message.fileUrl?.let { url -> onClickImage(url) }
                                    }

                                    MessageType.FILE.name -> {
                                        message.fileUrl?.let { url -> onClickFile(url) }
                                    }

                                    else -> onClick()
                                }
                            }
                        )
                        .padding(
                            start = if (message.type == MessageType.FILE.name
                                || message.type == MessageType.IMAGE.name
                            ) 0.dp else 14.dp,
                            end = if (message.type == MessageType.FILE.name
                                || message.type == MessageType.IMAGE.name
                            ) 0.dp else 14.dp,
                            top = if (message.type == MessageType.FILE.name
                                || message.type == MessageType.IMAGE.name
                            ) 0.dp else 10.dp,
                            bottom = if (message.type == MessageType.IMAGE.name
                                && !message.text.isNullOrBlank()
                            ) 10.dp
                            else if (message.type == MessageType.IMAGE.name) 0.dp
                            else 10.dp
                        )
                ) {
                    when (message.type) {
                        MessageType.FILE.name -> FileBubble(
                            message = message,
                            isMe = isMe,
                            avatarUrl = avatarUrl,
                            modifier = Modifier.align(Alignment.Center)
                        )

                        MessageType.TEXT.name -> Text(
                            text = message.text ?: "",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = if (isMe) Color.White else color.blackBackground,
                                lineHeight = 22.sp
                            )
                        )

                        MessageType.IMAGE.name -> ImageBubble(
                            message = message,
                            isMe = isMe
                        )
                    }
                }

                if (isMe && isLast) {
                    MessageStatusIcon(
                        status = message.status,
                        modifier = Modifier.padding(horizontal = 2.dp)
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = showTime,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Text(
                text = DateTimeUtils.formatTime(message.timestamp),
                style = MaterialTheme.typography.labelSmall.copy(color = color.gray),
                modifier = Modifier
                    .padding(
                        start = if (isMe) 0.dp else 40.dp,
                        top = 4.dp,
                        end = 16.dp
                    )
                    .fillMaxWidth(),
                textAlign = if (isMe) TextAlign.End else TextAlign.Start
            )
        }
    }
}

@Composable
private fun ImageBubble(
    message: MessageUiState,
    isMe: Boolean
) {
    val color = LocalExtendedColors.current
    Column {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        if (message.status == MessageStatus.SENDING
                            && message.fileUrl?.startsWith("content://") == true
                        ) {
                            message.fileUrl!!.toUri()
                        } else {
                            message.fileUrl
                        }
                    )
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = ColorPainter(Color(0xFFE0E0E0)),
                error = ColorPainter(Color(0xFFE0E0E0)),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp, max = 240.dp)
            )

            if (message.status == MessageStatus.SENDING) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Black.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }

        if (!message.text.isNullOrBlank()) {
            Text(
                text = message.text!!,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = if (isMe) Color.White else color.blackBackground,
                    lineHeight = 22.sp
                ),
                modifier = Modifier.padding(
                    start = 14.dp, end = 14.dp,
                    top = 8.dp, bottom = 0.dp
                )
            )
        }
    }
}

@Composable
fun MessageStatusIcon(
    status: MessageStatus,
    modifier: Modifier = Modifier
) {
    val tint = when (status) {
        MessageStatus.FAILED -> Color(0xFFE53935)
        else -> LocalExtendedColors.current.gray
    }

    when (status) {
        MessageStatus.SENT -> Icon(
            imageVector = Icons.Default.DoneAll,
            contentDescription = "Đã gửi",
            tint = tint,
            modifier = modifier.size(14.dp)
        )

        MessageStatus.SEEN -> Icon(
            imageVector = Icons.Default.RemoveRedEye,
            contentDescription = "Đã xem",
            tint = tint,
            modifier = modifier.size(14.dp)
        )

        MessageStatus.FAILED -> Icon(
            imageVector = Icons.Default.ErrorOutline,
            contentDescription = "Gửi thất bại",
            tint = tint,
            modifier = modifier.size(14.dp)
        )

        else -> Icon(
            imageVector = Icons.Default.IncompleteCircle,
            contentDescription = "Đang gửi",
            tint = tint,
            modifier = modifier.size(14.dp)
        )
    }
}