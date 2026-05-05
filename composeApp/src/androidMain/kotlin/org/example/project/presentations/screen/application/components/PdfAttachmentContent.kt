package org.example.project.presentations.screen.application.components

import android.net.Uri
import android.provider.OpenableColumns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import org.example.project.R
import org.example.project.presentations.screen.application.ApplicationState
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun PdfAttachmentContent(
    uiState: ApplicationState,
    onRemoveFile: () -> Unit,
    onOpenFilePicker: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier.padding(top = 15.dp)
    ) {
        Text(
            text = "Đính kèm file",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (uiState.attachedFile != null) {
            val uri = uiState.attachedFile.toUri()
            val fileName = remember(uri) {
                context.contentResolver
                    .query(uri, null, null, null, null)
                    ?.use { cursor ->
                        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        cursor.moveToFirst()
                        if (nameIndex >= 0) cursor.getString(nameIndex) else null
                    } ?: uri.lastPathSegment ?: "file.pdf"
            }

            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(110.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                        .background(LocalExtendedColors.current.white)
                        .padding(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_pdf),
                        contentDescription = "PDF file",
                        tint = LocalExtendedColors.current.red,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = fileName,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.DarkGray,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )
                }

                Surface(
                    onClick = onRemoveFile,
                    shape = CircleShape,
                    color = Color.Black.copy(alpha = 0.6f),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .size(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Xóa file",
                        tint = Color.White,
                        modifier = Modifier.padding(3.dp)
                    )
                }
            }
        } else {
            Surface(
                onClick = onOpenFilePicker,
                color = Color.Transparent,
                modifier = Modifier
                    .size(85.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .drawBehind {
                            val strokeWidth = 2.dp.toPx()
                            val cornerRadius = 8.dp.toPx()
                            drawRoundRect(
                                color = Color.Gray,
                                size = size,
                                cornerRadius = CornerRadius(cornerRadius),
                                style = Stroke(
                                    width = strokeWidth,
                                    pathEffect = PathEffect.dashPathEffect(
                                        floatArrayOf(8f, 6f), 0f
                                    )
                                )
                            )
                        }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Thêm file PDF",
                        tint = LocalExtendedColors.current.gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = if (uiState.attachedFile == null) "Chỉ chọn 1 file PDF" else "Đã chọn 1 file PDF",
            color = LocalExtendedColors.current.gray,
            style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
        )
    }
}