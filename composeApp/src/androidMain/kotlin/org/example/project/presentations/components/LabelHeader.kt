package org.example.project.presentations.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LabelHeader(label: String) {
    Column(
        modifier = Modifier
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(12.dp))
    }
}