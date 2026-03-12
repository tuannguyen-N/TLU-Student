package org.example.project.presentations.screen.features.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.FeatureEditMode
import org.example.project.domain.model.FeatureUiModel

@Composable
fun FeatureGrid(
    items: List<FeatureUiModel>,
    isEditing: Boolean = false,
    editMode: FeatureEditMode = FeatureEditMode.ADD,
    isQuickAccessFull: Boolean = false,
    isQuickAccessMin: Boolean = false,
    onClickFeature: (FeatureUiModel) -> Unit = {},
    onClickAddToQuickAccess: (FeatureUiModel) -> Unit = {},
    onClickRemoveFromQuickAccess: (FeatureUiModel) -> Unit = {}
) {
    val columns = 4
    val rows = (items.size + columns - 1) / columns

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        repeat(rows) { rowIndex ->
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                repeat(columns) { colIndex ->
                    val itemIndex = rowIndex * columns + colIndex
                    if (itemIndex < items.size) {
                        FeatureItem(
                            feature = items[itemIndex],
                            modifier = Modifier.weight(1f),
                            isEditing = isEditing,
                            editMode = editMode,
                            showEditIcon = when (editMode) {
                                FeatureEditMode.ADD -> !isQuickAccessFull
                                FeatureEditMode.REMOVE -> !isQuickAccessMin
                            },
                            onClickFeature = { onClickFeature(it) },
                            onClickEditMode = {
                                if (it == FeatureEditMode.ADD) onClickAddToQuickAccess(items[itemIndex])
                                else onClickRemoveFromQuickAccess(items[itemIndex])
                            }
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}