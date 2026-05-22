package org.example.project.presentations.screen.attendance_checking

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.Camera
import androidx.compose.foundation.background
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.presentations.components.LoadingView
import org.example.project.presentations.components.StatusBarStyle
import org.example.project.presentations.components.TopCenterScreenBar
import org.example.project.presentations.dialog.FailureDialog
import org.example.project.presentations.dialog.SuccessDialog
import org.example.project.presentations.screen.attendance_checking.components.CameraPreview
import org.example.project.presentations.screen.attendance_checking.components.LocationDisabledContent
import org.example.project.presentations.screen.attendance_checking.components.PermissionDeniedContent
import org.example.project.presentations.screen.attendance_checking.components.ScannerOverlay
import org.example.project.presentations.utils.CollectWithLifecycle

@Composable
fun CameraQrScreen(
    viewModel: CameraQrViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var isLocationEnabled by remember { mutableStateOf(viewModel.checkGpsEnabled()) }

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                isLocationEnabled = viewModel.checkGpsEnabled()
                hasLocationPermission = ContextCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                hasCameraPermission = ContextCompat.checkSelfPermission(
                    context, Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    DisposableEffect(context) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(ctx: Context?, intent: Intent?) {
                if (intent?.action == LocationManager.PROVIDERS_CHANGED_ACTION) {
                    isLocationEnabled = viewModel.checkGpsEnabled()
                }
            }
        }
        val filter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        context.registerReceiver(receiver, filter)

        onDispose {
            context.unregisterReceiver(receiver)
        }
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasLocationPermission = isGranted
            if (isGranted) isLocationEnabled = viewModel.checkGpsEnabled()
        }
    )

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasCameraPermission = isGranted
            if (isGranted && !hasLocationPermission) {
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    )

    var successMessage by remember { mutableStateOf<String?>(null) }
    var failureMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        } else if (!hasLocationPermission) {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    viewModel.event.CollectWithLifecycle { event ->
        when (event) {
            is CameraQrUiEvent.OnScanAndApiCompleted -> onBack()
            is CameraQrUiEvent.OnAttendanceSuccess ->
                successMessage = event.message.ifBlank { "Bạn đã điểm danh thành công!" }

            is CameraQrUiEvent.OnAttendanceFailure -> failureMessage =
                "Điểm danh thất bại, lý do: ${event.message}"
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
            onDismiss = {
                failureMessage = null
                viewModel.dismissError()
            })
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
            if (hasCameraPermission && hasLocationPermission && isLocationEnabled) {
                var camera by remember { mutableStateOf<Camera?>(null) }
                var isTorchEnabled by remember { mutableStateOf(false) }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    CameraPreview(
                        modifier = Modifier.fillMaxSize(),
                        lifecycleOwner = lifecycleOwner,
                        onCameraReady = { cam -> camera = cam },
                        onQrScanned = { rawValue ->
                            viewModel.onQrScanned(rawValue)
                        },
                        onError = { e -> Log.e("CameraQrScreen", "CameraPreview error", e) }
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
            } else if (hasCameraPermission && hasLocationPermission && !isLocationEnabled) {
                LocationDisabledContent(
                    onEnableLocation = {
                        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        context.startActivity(intent)
                    },
                    onOpenSettings = {
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", context.packageName, null)
                        )
                        context.startActivity(intent)
                    },
                    onBack = onBack
                )
            } else {
                PermissionDeniedContent(
                    onRequestPermission = {
                        if (!hasCameraPermission) {
                            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                        } else if (!hasLocationPermission) {
                            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        }
                    },
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
        }
    }
}