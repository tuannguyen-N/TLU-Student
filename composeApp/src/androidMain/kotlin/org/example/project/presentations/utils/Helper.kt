package org.example.project.presentations.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set
import java.io.ByteArrayOutputStream

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

suspend fun <T> withDelayedLoading(
    delayMs: Long = 200,
    onLoading: (Boolean) -> Unit,
    block: suspend () -> T
): T {
    var loadingJob: Job? = null
    return try {
        loadingJob = CoroutineScope(currentCoroutineContext()).launch {
            delay(delayMs)
            onLoading(true)
        }
        val result = block()
        loadingJob.cancel()
        onLoading(false)
        result
    } catch (e: Exception) {
        loadingJob?.cancel()
        onLoading(false)
        throw e
    }
}

fun generateQrBitmap(text: String, size: Int = 512): Bitmap {
    val writer = QRCodeWriter()
    val bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, size, size)

    val bmp = createBitmap(size, size, Bitmap.Config.RGB_565)
    for (x in 0 until size) {
        for (y in 0 until size) {
            bmp[x, y] = if (bitMatrix[x, y]) Color.Black.toArgb() else Color.White.toArgb()
        }
    }
    return bmp
}

fun Bitmap.toByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}