package org.example.project.presentations.screen.timetable.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun DropDownPopup(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Popup(
            alignment = Alignment.TopCenter,
            onDismissRequest = onDismiss,
            properties = PopupProperties(focusable = true)
        ) {
            Card(
                modifier = Modifier
                    .width(220.dp)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                LazyColumn {
                    itemsIndexed(items) { index, item ->
                        val isSelected = item == selectedItem

                        Column(
                            modifier = Modifier.background(
                                if (isSelected) Color(0xFFF0F6FF) else Color.White
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onItemSelected(item)
                                        onDismiss()
                                    }
                                    .padding(horizontal = 16.dp, vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = item,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = if (isSelected) FontWeight.SemiBold
                                        else FontWeight.Normal,
                                        color = if (isSelected)
                                            LocalExtendedColors.current.fontBlue
                                        else
                                            Color(0xFF1A1A2E)
                                    )
                                )
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = LocalExtendedColors.current.fontBlue,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }

                            if (index < items.lastIndex) {
                                HorizontalDivider(
                                    color = Color(0xFFF3F4F6),
                                    thickness = 1.dp,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}