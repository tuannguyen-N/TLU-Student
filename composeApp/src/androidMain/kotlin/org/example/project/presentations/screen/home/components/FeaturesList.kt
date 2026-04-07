package org.example.project.presentations.screen.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.FeatureType
import org.example.project.domain.model.FeatureUiModel
import org.example.project.presentations.screen.features.components.FeatureGrid
import org.example.project.presentations.theme.ExtendedColors
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun FeatureList(
    modifier: Modifier = Modifier,
    color: ExtendedColors,
    items: List<FeatureUiModel>,
    onClickItem: (FeatureType) -> Unit = {},
    onClickAll: () -> Unit = {}
) {
    Column(modifier = modifier) {
        Row(
            modifier = modifier
                .padding(horizontal = 15.dp)
                .padding(bottom = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Chức năng",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "Tất cả",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = LocalExtendedColors.current.fontBlue,
                modifier = Modifier.clickable(
                    onClick = onClickAll
                )
            )
        }

        FeatureGrid(
            color = color,
            items = items,
            onClickFeature = { onClickItem(it.type) },
            modifier = Modifier.padding(horizontal = 15.dp)
        )
    }
}