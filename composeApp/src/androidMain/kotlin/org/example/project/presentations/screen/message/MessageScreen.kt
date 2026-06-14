package org.example.project.presentations.screen.message

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import org.example.project.presentations.components.LoadingView
import org.example.project.presentations.screen.message.components.EmptyMessageContent
import org.example.project.presentations.screen.message.components.ImageViewerDialog
import org.example.project.presentations.screen.message.components.MessageContent
import org.example.project.presentations.screen.message.components.MessageInputBar
import org.example.project.presentations.screen.message.components.MessageTopBar
import org.example.project.presentations.screen.message.components.StudentInfoSection
import org.example.project.presentations.screen.message.components.VideoViewerDialog
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun MessageScreen(
    viewModel: MessageViewModel,
    onBack: () -> Unit,
    onClickFile: (String) -> Unit
) {
    val messages by viewModel.messages.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var selectedImageUrl by remember { mutableStateOf<String?>(null) }
    var selectedVideoUrl by remember { mutableStateOf<String?>(null) }

    var isTopBarExpanded by remember { mutableStateOf(false) }
    var topBarHeightPx by remember { mutableIntStateOf(0) }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build()
    }

    var playingVideoUrl by remember { mutableStateOf<String?>(null) }

    val onPlayVideoInline = remember {
        { url: String ->
            if (playingVideoUrl != url) {
                playingVideoUrl = url
                exoPlayer.setMediaItem(MediaItem.fromUri(url.toUri()))
                exoPlayer.prepare()
            }
            exoPlayer.play()
        }
    }

    val onPauseVideoInline = remember {
        {
            exoPlayer.pause()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    BackHandler(enabled = isTopBarExpanded) {
        isTopBarExpanded = false
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = LocalExtendedColors.current.background,
            contentWindowInsets = WindowInsets(0),
            topBar = {
                Box(
                    modifier = Modifier.onGloballyPositioned { coordinates ->
                        topBarHeightPx = coordinates.size.height
                    }
                ) {
                    MessageTopBar(
                        onBack = onBack,
                        chatUser = uiState.chatUser!!,
                        isExpanded = isTopBarExpanded,
                        onExpandChange = { isTopBarExpanded = it },
                        expandedContent = {
                            uiState.chatStudent?.let { StudentInfoSection(student = it) }
                        }
                    )
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                when {
                    uiState.isLoading -> LoadingView()
                    messages.isEmpty() -> EmptyMessageContent(
                        modifier = Modifier.weight(1f)
                    )

                    else -> MessageContent(
                        messages = messages,
                        modifier = Modifier.weight(1f),
                        onClickFile = onClickFile,
                        onClickImage = { url -> selectedImageUrl = url },
                        onClickVideo = { url ->
                            if (playingVideoUrl != url) {
                                playingVideoUrl = url
                                exoPlayer.setMediaItem(MediaItem.fromUri(url.toUri()))
                                exoPlayer.prepare()
                            }
                            selectedVideoUrl = url
                        },
                        onLoadMoreMessage = viewModel::loadMoreMessages,
                        isLoadingMore = uiState.isLoadingMore,
                        hasMoreMessages = uiState.hasMoreMessages,
                        chatUser = uiState.chatUser,
                        isAiReplying = uiState.isAiReplying,
                        onSummarize = viewModel::summarize,
                        exoPlayer = exoPlayer,
                        playingVideoUrl = playingVideoUrl,
                        onPlayVideoInline = onPlayVideoInline,
                        onPauseVideoInline = onPauseVideoInline,
                        isDialogVisible = selectedVideoUrl != null
                    )
                }
                MessageInputBar(
                    state = uiState,
                    onMessageChange = { viewModel.onMessageChange(it) },
                    onSend = viewModel::onSend,
                    onImagePick = { viewModel.onImageSelected(it, context) },
                    onRemoveImage = viewModel::onRemoveImage,
                    onFilePick = { viewModel.onFileSelected(it, context) },
                    onRemoveFile = viewModel::onRemoveFile,
                    onVideoPick = { viewModel.onVideoSelected(it, context) },
                    onRemoveVideo = viewModel::onRemoveVideo,
                    modifier = Modifier
                        .navigationBarsPadding()
                        .imePadding()
                )
            }
        }

        AnimatedVisibility(
            visible = isTopBarExpanded,
            enter = fadeIn(tween(200)),
            exit = fadeOut(tween(200))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = with(LocalDensity.current) { topBarHeightPx.toDp() })
                    .background(Color.Black.copy(alpha = 0.4f))
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        isTopBarExpanded = false
                    }
            )
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

        selectedVideoUrl?.let { url ->
            VideoViewerDialog(
                videoUrl = url,
                onDismiss = { selectedVideoUrl = null },
                onDownload = { videoUrl ->
                    val fileName = "VID_${System.currentTimeMillis()}.mp4"
                    downloadVideo(context, videoUrl, fileName)
                    Toast.makeText(context, "Đã tải xong!", Toast.LENGTH_SHORT).show()
                },
                exoPlayer = exoPlayer
            )
        }
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

fun downloadVideo(context: Context, url: String, fileName: String) {
    val request = DownloadManager.Request(url.toUri())
        .setTitle(fileName)
        .setDescription("Đang tải video...")
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setDestinationInExternalPublicDir(Environment.DIRECTORY_MOVIES, fileName)
        .setAllowedOverMetered(true)
    val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    dm.enqueue(request)
}