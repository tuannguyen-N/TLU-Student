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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.domain.model.Message
import org.example.project.domain.model.MessageType
import org.example.project.domain.model.MessageUiState
import org.example.project.domain.utils.DateTimeUtils
import org.example.project.presentations.theme.LocalExtendedColors


@Composable
fun FileBubble(message: MessageUiState) {
    val color = LocalExtendedColors.current
    Row(
        modifier = Modifier
            .background(color.midBlue, RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color.lightBlue),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AttachFile,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = message.fileName.orEmpty(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            )
            Text(
                text = message.fileSize.orEmpty(),
                style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.75f))
            )
        }
    }
}

@Composable
fun MessageBubble(
    message: MessageUiState,
    showTime: Boolean,
    onClick: () -> Unit
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
            if (!isMe) {
                Box(
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFBDBDBD)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "N",
                        style = MaterialTheme.typography.labelMedium.copy(color = Color.White)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .widthIn(max = 280.dp)
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
                        onClick = onClick
                    )
                    .then(
                        if (message.type == MessageType.FILE.name) Modifier
                        else Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                    )
            ) {
                when (message.type) {
                    MessageType.FILE.name -> FileBubble(message = message)
                    MessageType.TEXT.name -> Text(
                        text = message.text ?: "",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = if (isMe) Color.White else color.blackBackground,
                            lineHeight = 22.sp
                        )
                    )

                    MessageType.IMAGE.name -> Text(
                        text = "[Hình ảnh]",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = if (isMe) Color.White else color.blackBackground,
                            lineHeight = 22.sp
                        )
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
                        start = if (isMe) 0.dp else 38.dp,
                        end = if (isMe) 0.dp else 0.dp,
                        top = 4.dp
                    )
                    .fillMaxWidth(),
                textAlign = if (isMe) TextAlign.End else TextAlign.Start
            )
        }
    }
}