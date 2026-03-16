package org.example.project.presentations.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

val vietnameseDayLabels = listOf("Th 2", "Th 3", "Th 4", "Th 5", "Th 6", "Th 7", "CN")

@Composable
fun rememberSafeClick(
    debounceTime: Long = 400L,
    onClick: () -> Unit
): () -> Unit {
    var lastClickTime by remember { mutableLongStateOf(0L) }
    return remember(onClick) {
        {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > debounceTime){
                lastClickTime = currentTime
                onClick()
            }
        }
    }
}