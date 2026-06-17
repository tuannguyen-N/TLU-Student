package org.example.project.presentations.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.R
import org.example.project.presentations.theme.LocalExtendedColors

data class TabItem(
    val icon: Int,
    val label: String? = null
)

@Composable
fun BottomBar(
    currentPage: Int = 0,
    onTabSelected: (Int) -> Unit = {}
) {
    val tabs = listOf(
        TabItem(R.drawable.icon_home, "Home"),
        TabItem(R.drawable.icon_calendar, "Schedule"),
        TabItem(R.drawable.icon_chat, "Chat"),
        TabItem(R.drawable.icon_transcript, "Transcript")
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .background(LocalExtendedColors.current.mainBlue)
                .height(56.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabs.forEachIndexed { index, tab ->
                BottomItem(
                    drawRes = tab.icon,
                    label = tab.label,
                    selected = currentPage == index,
                    onClick = { onTabSelected(index) }
                )
            }
        }
    }
}

@Composable
fun BottomItem(
    drawRes: Int,
    label: String?,
    selected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (selected) Color.White else Color.Transparent,
        label = "bgAnimation"
    )

    val contentColor by animateColorAsState(
        targetValue = if (selected) LocalExtendedColors.current.mainBlue else Color.White.copy(alpha = 0.7f),
        label = "contentColorAnimation"
    )

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(backgroundColor)
            .clickable(
                indication = ripple(bounded = true),
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() }
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
            .padding(
                horizontal = if (selected && label != null) 16.dp else 10.dp,
                vertical = 10.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Image(
            painter = painterResource(drawRes),
            contentDescription = "icon_bottom_bar",
            colorFilter = ColorFilter.tint(contentColor),
            modifier = Modifier.size(20.dp)
        )

        AnimatedVisibility(
            visible = selected && label != null,
            enter = fadeIn(animationSpec = tween(150, delayMillis = 100)),
            exit = fadeOut(animationSpec = tween(100))
        ) {
            Text(
                text = label.orEmpty(),
                color = contentColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1
            )
        }
    }
}