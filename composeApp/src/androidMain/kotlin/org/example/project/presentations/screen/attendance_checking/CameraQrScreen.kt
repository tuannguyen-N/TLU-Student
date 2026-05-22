package org.example.project.presentations.screen.attendance_checking

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FlashOff
import androidx.compose.material.icons.outlined.FlashOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import org.example.project.presentations.components.LoadingView
import org.example.project.presentations.components.StatusBarStyle
import org.example.project.presentations.components.TopCenterScreenBar
import org.example.project.presentations.dialog.FailureDialog
import org.example.project.presentations.dialog.SuccessDialog
import org.example.project.presentations.screen.attendance_checking.components.PermissionDeniedContent
import org.example.project.presentations.screen.attendance_checking.components.ScannerOverlay
import org.example.project.presentations.utils.CollectWithLifecycle
import java.util.concurrent.Executors

@Composable
fun CameraQrScreen(
    viewModel: CameraQrViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasCameraPermission = isGranted
        }
    )

    var successMessage by remember { mutableStateOf<String?>(null) }
    var failureMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    viewModel.event.CollectWithLifecycle { event ->
        when (event) {
            is CameraQrUiEvent.OnScanAndApiCompleted -> onBack()
            is CameraQrUiEvent.OnAttendanceSuccess ->
                successMessage = "Bạn đã điểm danh thành công!"

            is CameraQrUiEvent.OnAttendanceFailure -> failureMessage =
                "Điểm danh thất bại, vui lòng thử lại sau!"
        }
    }

    successMessage?.let { message ->
        SuccessDialog(
            title = "Thành công!",
            message = message,
            onDismiss = {
                onBack()
                successMessage = null
            })
    }

    failureMessage?.let { message ->
        FailureDialog(
            title = "Thất bại",
            message = message,
            onDismiss = { failureMessage = null })
    }

    StatusBarStyle(darkIcons = false)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Black
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (hasCameraPermission) {
                var camera by remember { mutableStateOf<Camera?>(null) }
                var isTorchEnabled by remember { mutableStateOf(false) }
                var zoomRatio by remember { mutableFloatStateOf(1f) }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTransformGestures { _, _, zoom, _ ->
                                camera?.cameraInfo?.zoomState?.value?.let { zoomState ->
                                    val currentZoom = zoomState.zoomRatio
                                    val newZoom = (currentZoom * zoom)
                                        .coerceIn(
                                            zoomState.minZoomRatio,
                                            zoomState.maxZoomRatio
                                        )

                                    zoomRatio = newZoom
                                    camera?.cameraControl?.setZoomRatio(newZoom)
                                }
                            }
                        }
                ) {
                    AndroidView(
                        factory = { ctx ->
                            val previewView = PreviewView(ctx).apply {
                                scaleType = PreviewView.ScaleType.FILL_CENTER
                            }
                            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                            val executor = ContextCompat.getMainExecutor(ctx)
                            val analysisExecutor = Executors.newSingleThreadExecutor()

                            cameraProviderFuture.addListener({
                                val cameraProvider = cameraProviderFuture.get()
                                val preview = Preview.Builder().build().also {
                                    it.setSurfaceProvider(previewView.surfaceProvider)
                                }

                                val imageAnalysis = ImageAnalysis.Builder()
                                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                    .build()

                                imageAnalysis.setAnalyzer(analysisExecutor) { imageProxy ->
                                    val mediaImage = imageProxy.image
                                    if (mediaImage != null) {
                                        val image = InputImage.fromMediaImage(
                                            mediaImage,
                                            imageProxy.imageInfo.rotationDegrees
                                        )
                                        val scanner = BarcodeScanning.getClient()
                                        scanner.process(image)
                                            .addOnSuccessListener { barcodes ->
                                                for (barcode in barcodes) {
                                                    val rawValue = barcode.rawValue
                                                    if (!rawValue.isNullOrBlank()) {
                                                        executor.execute {
                                                            viewModel.onQrScanned(rawValue)
                                                        }
                                                        break
                                                    }
                                                }
                                            }
                                            .addOnFailureListener { e ->
                                                Log.e("CameraQrScreen", "QR scan error", e)
                                            }
                                            .addOnCompleteListener {
                                                imageProxy.close()
                                            }
                                    } else {
                                        imageProxy.close()
                                    }
                                }

                                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                                try {
                                    cameraProvider.unbindAll()
                                    camera = cameraProvider.bindToLifecycle(
                                        lifecycleOwner,
                                        cameraSelector,
                                        preview,
                                        imageAnalysis
                                    )
                                } catch (e: Exception) {
                                    Log.e("CameraQrScreen", "Use case binding failed", e)
                                }
                            }, executor)

                            previewView
                        },
                        modifier = Modifier.fillMaxSize()
                    )

                    ScannerOverlay()

                    TopCenterScreenBar(
                        title = "Quét mã QR điểm danh",
                        backgroundColor = Color.Transparent,
                        contentColor = Color.White,
                        onBack = onBack,
                        enablePaddingStatus = false
                    )

                    IconButton(
                        onClick = {
                            isTorchEnabled = !isTorchEnabled
                            camera?.cameraControl?.enableTorch(isTorchEnabled)
                        },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 80.dp)
                            .size(56.dp)
                            .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                    ) {
                        Icon(
                            imageVector = if (isTorchEnabled) Icons.Outlined.FlashOn else Icons.Outlined.FlashOff,
                            contentDescription = "Flashlight",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            } else {
                PermissionDeniedContent(
                    onRequestPermission = { permissionLauncher.launch(Manifest.permission.CAMERA) },
                    onOpenSettings = {
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", context.packageName, null)
                        )
                        context.startActivity(intent)
                    },
                    onBack = onBack
                )
            }

            if (uiState.isLoading) {
                LoadingView()
            }

            if (uiState.isError) {
                FailureDialog(
                    title = "Điểm danh thất bại",
                    message = uiState.errorMessage ?: "Không thể kết nối đến máy chủ",
                    onDismiss = { viewModel.dismissError() }
                )
            }
        }
    }
}