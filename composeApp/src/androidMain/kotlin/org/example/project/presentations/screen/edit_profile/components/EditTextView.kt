package org.example.project.presentations.screen.edit_profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun EditTextView(
    iconRes: Int,
    title: String,
    value: String,
    hint: String,
    error: String? = null,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(top = 15.dp, start = 20.dp, end = 20.dp)
            .fillMaxWidth()
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.bodyLarge,
            placeholder = {
                Text(
                    text = hint,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    tint = LocalExtendedColors.current.gray
                )
            },
            isError = error != null,
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (error != null) Color.Red else LocalExtendedColors.current.mainBlue,
                unfocusedBorderColor = if (error != null) Color.Red else LocalExtendedColors.current.gray.copy(alpha = 0.5f),

                cursorColor = MaterialTheme.colorScheme.primary,

                focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                unfocusedLeadingIconColor = LocalExtendedColors.current.gray,

                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            )
        )

        if (error != null) {
            Text(
                text = error,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}