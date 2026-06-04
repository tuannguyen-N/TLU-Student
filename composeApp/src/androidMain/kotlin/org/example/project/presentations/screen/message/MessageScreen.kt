package org.example.project.presentations.screen.message

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.presentations.components.LoadingView
import org.example.project.presentations.screen.message.components.EmptyMessageContent
import org.example.project.presentations.screen.message.components.ImageViewerDialog
import org.example.project.presentations.screen.message.components.MessageContent
import org.example.project.presentations.screen.message.components.MessageInputBar
import org.example.project.presentations.screen.message.components.MessageTopBar
import org.example.project.presentations.screen.message.components.StudentInfoSection
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun MessageScreen(
    viewModel: MessageViewModel,
    onBack: () -> Unit,
    onClickFile: (String) -> Unit
) {
    val messages by viewModel.messages.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val chatUser by viewModel.chatUser.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val isLoadingMore by viewModel.isLoadingMore.collectAsStateWithLifecycle()

    var selectedImageUrl by remember { mutableStateOf<String?>(null) }

    Scaffold(
        containerColor = LocalExtendedColors.current.background,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            Column {
                MessageTopBar(onBack = onBack, chatUser = chatUser)
                uiState.chatStudent?.let { StudentInfoSection(student = it) }
            }
        },
        bottomBar = {
            MessageInputBar(
                state = uiState,
                onMessageChange = viewModel::onMessageChange,
                onSend = viewModel::onSend,
                onImagePick = { viewModel.onImageSelected(it, context) },
                onRemoveImage = viewModel::onRemoveImage,
                onFilePick = { viewModel.onFileSelected(it, context) },
                onRemoveFile = viewModel::onRemoveFile
            )
        }
    ) { innerPadding ->
        when {
            uiState.isLoading -> LoadingView()
            messages.isEmpty() -> EmptyMessageContent(modifier = Modifier.padding(innerPadding))
            else -> MessageContent(
                messages = messages,
                modifier = Modifier.padding(innerPadding),
                onClickFile = onClickFile,
                onClickImage = { url -> selectedImageUrl = url },
                onLoadMoreMessage = viewModel::loadMoreMessages,
                isLoadingMore = isLoadingMore
            )
        }
    }

    selectedImageUrl?.let { url ->
        ImageViewerDialog(
            imageUrl = url,
            onDismiss = { selectedImageUrl = null },
            onDownload = { imageUrl ->
                val fileName = "IMG_${System.currentTimeMillis()}.jpg"
                downloadImage(context, imageUrl, fileName)
                Toast.makeText(context, "Đã tải xong!", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

fun downloadImage(context: Context, url: String, fileName: String) {
    val request = DownloadManager.Request(url.toUri())
        .setTitle(fileName)
        .setDescription("Đang tải xuống...")
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, fileName)
        .setMimeType("image/jpeg")
        .setAllowedOverMetered(true)
        .setAllowedOverRoaming(true)

    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    downloadManager.enqueue(request)
}