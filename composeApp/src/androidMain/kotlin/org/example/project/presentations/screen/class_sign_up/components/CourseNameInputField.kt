package org.example.project.presentations.screen.class_sign_up.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun CourseNameInputField(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    mainBlue: Color,
    gray: Color
) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = "Tìm kiếm môn học...",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        singleLine = true,
        shape = MaterialTheme.shapes.medium,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = LocalExtendedColors.current.white,
            focusedContainerColor = LocalExtendedColors.current.white,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = mainBlue,
            unfocusedLeadingIconColor = gray,
            focusedLeadingIconColor = mainBlue
        )
    )
}