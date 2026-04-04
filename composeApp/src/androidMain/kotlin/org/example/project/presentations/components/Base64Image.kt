package org.example.project.presentations.components

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap

@Composable
fun Base64Image(
    base64String: String,
    modifier: Modifier = Modifier
) {
    val bitmap = remember(base64String) {
        val cleanBase64 = base64String
            .removePrefix("data:image/png;base64,")
            .removePrefix("data:image/jpeg;base64,")

        val bytes = Base64.decode(cleanBase64, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    bitmap?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = null,
            modifier = modifier
        )
    }
}