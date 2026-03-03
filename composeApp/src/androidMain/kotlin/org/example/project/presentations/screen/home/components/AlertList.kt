package org.example.project.presentations.screen.home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.example.project.domain.model.AlertUiModel

@Composable
fun AlertList(
    items: List<AlertUiModel>,
    onClickAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth()
    ) {
        itemsIndexed(
            items = items,
            key = { index, _ -> index },
            contentType = { _, item -> item.isHighAlert }
        ) { _, item ->
            when {
                item.isHighAlert -> {
                    HighAlertCard(
                        item = item,
                        onClickAction = onClickAction
                    )
                }

                else -> {
                    MediumAlertCard(
                        item = item,
                        onClickAction = onClickAction
                    )
                }
            }
        }
    }
}