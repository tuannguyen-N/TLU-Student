package org.example.project.presentations.screen.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.AlertUiModel
import org.example.project.domain.model.NotificationReferenceType
import org.example.project.domain.model.NotificationSeverity
import org.example.project.presentations.components.shimmerEffect

@Composable
fun AlertList(
    modifier: Modifier = Modifier,
    items: List<AlertUiModel>,
    isLoading: Boolean = false,
    onClickAction: () -> Unit = {}
) {
    val groupedItems = remember(items) {
        items
            .filter { it.severity != NotificationSeverity.NORMAL }
            .groupBy { it.severity }
            .map { (_, group) -> group.first() to group.size }
    }

    if (isLoading) {
        Column(modifier = modifier.fillMaxWidth()) {
            repeat(2) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                        .fillMaxWidth()
                        .height(76.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .shimmerEffect()
                )
            }
        }
    } else {
        Column(modifier = modifier.fillMaxWidth()) {
            groupedItems.forEach { (item, count) ->
                AlertCard(
                    item = item,
                    extraCount = count - 1,
                    onClickAction = onClickAction
                )
            }
        }
    }
}