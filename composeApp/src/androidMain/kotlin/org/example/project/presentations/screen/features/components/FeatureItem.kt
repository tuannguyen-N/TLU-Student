package org.example.project.presentations.screen.features.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.domain.model.FeatureEditMode
import org.example.project.domain.model.FeatureUiModel
import org.example.project.presentations.theme.LocalExtendedColors
import org.example.project.presentations.utils.toIconBackgroundColor
import org.example.project.presentations.utils.toIconTintColor
import org.example.project.presentations.utils.toImageVector

@Composable
fun FeatureItem(
    modifier: Modifier = Modifier,
    feature: FeatureUiModel,
    isEditing: Boolean = false,
    editMode: FeatureEditMode,
    showEditIcon: Boolean = true,
    onClickFeature: (FeatureUiModel) -> Unit,
    onClickEditMode: (FeatureEditMode) -> Unit
) {
    val red = LocalExtendedColors.current.red
    val green = LocalExtendedColors.current.green

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box {
            Surface(
                onClick = { if (!isEditing) onClickFeature(feature) },
                color = feature.type.toIconBackgroundColor(),
                modifier = Modifier
                    .size(65.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = feature.type.toImageVector(),
                        contentDescription = feature.name,
                        tint = feature.type.toIconTintColor(),
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            androidx.compose.animation.AnimatedVisibility(
                visible = isEditing && showEditIcon,
                enter = scaleIn(animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)),
                exit = scaleOut(),
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .clip(CircleShape)
                        .background(
                            if (editMode == FeatureEditMode.REMOVE) red
                            else green
                        )
                        .clickable { onClickEditMode(editMode) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (editMode == FeatureEditMode.REMOVE)
                            Icons.Rounded.Remove
                        else
                            Icons.Rounded.Add,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = feature.name,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
            textAlign = TextAlign.Center,
            color = LocalExtendedColors.current.gray,
            maxLines = 2
        )
    }
}