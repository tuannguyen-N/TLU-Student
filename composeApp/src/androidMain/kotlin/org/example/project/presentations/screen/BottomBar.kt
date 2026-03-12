package org.example.project.presentations.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.example.project.R
import org.example.project.presentations.theme.LocalExtendedColors

@Preview
@Composable
fun BottomBar(
    currentPage: Int = 0,
    onTabSelected: (Int) -> Unit = {}
) {
    val tabs = listOf(
        R.drawable.icon_home,
        R.drawable.icon_calendar,
        R.drawable.icon_chat,
        R.drawable.icon_transcript
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
                .height(60.dp)
                .width(250.dp)
                .background(LocalExtendedColors.current.mainBlue),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabs.forEachIndexed { index, drawRes ->
                BottomIcon(
                    drawRes = drawRes,
                    selected = currentPage == index,
                    onClick = { onTabSelected(index) }
                )
            }
        }
    }
}

@Composable
fun BottomIcon(
    drawRes: Int,
    selected: Boolean,
    onClick: () -> Unit
) {
    val animatedBackground by animateColorAsState(
        targetValue = if (selected)
            LocalExtendedColors.current.red
        else
            Color.Transparent,
        label = "bgAnimation"
    )

    val animatedIconColor by animateColorAsState(
        targetValue = if (selected)
            LocalExtendedColors.current.white
        else
            LocalExtendedColors.current.gray,
        label = "iconColorAnimation"
    )

    val scale by animateFloatAsState(
        targetValue = if (selected) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "scaleAnimation"
    )

    Box(
        modifier = Modifier
            .size(40.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(animatedBackground)
            .clickable(
                indication = ripple(bounded = true, radius = 24.dp),
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(drawRes),
            contentDescription = "icon_bottom_bar",
            colorFilter = ColorFilter.tint(animatedIconColor),
            modifier = Modifier.size(24.dp)
        )
    }
}
