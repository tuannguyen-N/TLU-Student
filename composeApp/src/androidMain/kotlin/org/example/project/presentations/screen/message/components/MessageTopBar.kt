package org.example.project.presentations.screen.message.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import androidx.compose.ui.unit.lerp
import coil.compose.AsyncImage
import org.example.project.R
import org.example.project.domain.model.UserUiModel
import org.example.project.presentations.theme.ExtendedColors
import org.example.project.presentations.theme.LocalExtendedColors
import org.example.project.presentations.utils.DateTimeUtils.formatLastSeenTopBar

@SuppressLint("RestrictedApi")
@Composable
fun MessageTopBar(
    chatUser: UserUiModel,
    onBack: () -> Unit = {},
    isExpanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    expandedContent: @Composable (() -> Unit)? = null
) {
    val color = LocalExtendedColors.current

    val avatarSize by animateDpAsState(
        targetValue = if (isExpanded) 72.dp else 40.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "avatarSize"
    )

    val expandProgress by animateFloatAsState(
        targetValue = if (isExpanded) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "expandProgress"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .statusBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
                .height(lerp(64.dp, 140.dp, expandProgress))
        ) {
            IconButton(
                // Nếu đang expand thì collapse, không thì back
                onClick = { if (isExpanded) onExpandChange(false) else onBack() },
                modifier = Modifier
                    .align(Alignment.CenterStart)
            ) {
                // Icon cũng animate từ arrow → close khi expand
                val iconRotation by animateFloatAsState(
                    targetValue = if (isExpanded) 90f else 0f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    ),
                    label = "iconRotation"
                )
                Icon(
                    imageVector = if (isExpanded) Icons.Default.Close
                    else Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = if (isExpanded) "Close" else "Back",
                    tint = color.midBlue,
                    modifier = Modifier.rotate(iconRotation)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .layout { measurable, constraints ->
                        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)
                        val placeable = measurable.measure(looseConstraints)
                        layout(constraints.maxWidth, constraints.maxHeight) {
                            val iconButtonWidth = 48.dp.roundToPx()
                            val collapsedX = iconButtonWidth + 4.dp.roundToPx()
                            val collapsedY = (constraints.maxHeight - placeable.height) / 2
                            val expandedX = (constraints.maxWidth - placeable.width) / 2
                            val expandedY = 16.dp.roundToPx()
                            val x = lerp(collapsedX.toFloat(), expandedX.toFloat(), expandProgress).toInt()
                            val y = lerp(collapsedY.toFloat(), expandedY.toFloat(), expandProgress).toInt()
                            placeable.placeRelative(x, y)
                        }
                    }
            ) {
                AvatarView(
                    chatUser = chatUser,
                    size = avatarSize,
                    color = color,
                    onClick = { onExpandChange(!isExpanded) }
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .layout { measurable, constraints ->
                        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)
                        val placeable = measurable.measure(looseConstraints)
                        layout(constraints.maxWidth, constraints.maxHeight) {
                            val iconButtonWidth = 48.dp.roundToPx()
                            val avatarCollapsedWidth = 40.dp.roundToPx()
                            val avatarExpandedWidth = 72.dp.roundToPx()
                            val gap = 10.dp.roundToPx()
                            val collapsedX = iconButtonWidth + avatarCollapsedWidth + gap + 4.dp.roundToPx()
                            val collapsedY = (constraints.maxHeight - placeable.height) / 2
                            val expandedX = (constraints.maxWidth - placeable.width) / 2
                            val expandedY = 16.dp.roundToPx() + avatarExpandedWidth + 8.dp.roundToPx()
                            val x = lerp(collapsedX.toFloat(), expandedX.toFloat(), expandProgress).toInt()
                            val y = lerp(collapsedY.toFloat(), expandedY.toFloat(), expandProgress).toInt()
                            placeable.placeRelative(x, y)
                        }
                    }
            ) {
                NamePresenceColumn(
                    chatUser = chatUser,
                    color = color,
                    centerAlign = isExpanded,
                    modifier = Modifier.graphicsLayer {
                        val textScale = lerp(1f, 1.05f, expandProgress)
                        scaleX = textScale
                        scaleY = textScale
                    }
                )
            }
        }

        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn(tween(300)) + expandVertically(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            ),
            exit = fadeOut(tween(200)) + shrinkVertically(tween(300))
        ) {
            expandedContent?.invoke()
        }

        HorizontalDivider(color = color.gray.copy(alpha = 0.2f))
    }
}

@Composable
private fun AvatarView(
    chatUser: UserUiModel,
    size: Dp,
    color: ExtendedColors,
    onClick: () -> Unit
) {
    if (!chatUser.avatarUrl.isNullOrEmpty()) {
        AsyncImage(
            model = chatUser.avatarUrl,
            contentDescription = "Avatar",
            placeholder = painterResource(R.drawable.icon_teacher_notification),
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .clickable(onClick = onClick),
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(color.gray)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = chatUser.name.firstOrNull()?.uppercase() ?: "",
                style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
            )
        }
    }
}

@Composable
private fun NamePresenceColumn(
    chatUser: UserUiModel,
    color: ExtendedColors,
    centerAlign: Boolean = false,
    modifier: Modifier = Modifier
) {
    val textAlign = if (centerAlign) TextAlign.Center else TextAlign.Start
    val horizontalAlignment = if (centerAlign) Alignment.CenterHorizontally else Alignment.Start

    Column(
        horizontalAlignment = horizontalAlignment,
        modifier = modifier
    ) {
        Text(
            text = "${chatUser.studentCode} - ${chatUser.name}",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                textAlign = textAlign
            )
        )
        when {
            chatUser.isOnline -> Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(color.green)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Đang hoạt động",
                    style = MaterialTheme.typography.bodySmall.copy(color = color.gray)
                )
            }
            else -> {
                val presenceText = formatLastSeenTopBar(chatUser.lastSeen)
                if (presenceText != null) {
                    Text(
                        text = "Hoạt động $presenceText",
                        style = MaterialTheme.typography.bodySmall.copy(color = color.gray)
                    )
                }
            }
        }
    }
}