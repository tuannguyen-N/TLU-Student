package org.example.project.presentations.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun TabRowView(
    tabs: List<Pair<String, ImageVector?>>,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabCount = tabs.size

    val indicatorOffset by animateFloatAsState(
        targetValue = selectedTab.toFloat() / tabCount,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "tab_indicator"
    )

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(45.dp)
            .padding(horizontal = 30.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(LocalExtendedColors.current.gray.copy(alpha = 0.1f))
            .padding(4.dp)
    ) {
        val tabWidth = maxWidth / tabCount

        Box(
            modifier = Modifier
                .width(tabWidth)
                .fillMaxHeight()
                .offset(x = tabWidth * indicatorOffset * tabCount)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            tabs.forEachIndexed { index, tab ->
                val isSelected = index == selectedTab

                val contentColor by animateColorAsState(
                    targetValue = if (isSelected)
                        LocalExtendedColors.current.fontBlue
                    else
                        LocalExtendedColors.current.gray,
                    animationSpec = tween(durationMillis = 200),
                    label = "tab_color_$index"
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onTabSelected(index) }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        tab.second?.let {
                            Icon(
                                imageVector = it,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                                tint = contentColor
                            )
                            Spacer(Modifier.width(5.dp))
                        }
                        Text(
                            text = tab.first,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            style = MaterialTheme.typography.titleSmall,
                            color = contentColor
                        )
                    }
                }
            }
        }
    }
}