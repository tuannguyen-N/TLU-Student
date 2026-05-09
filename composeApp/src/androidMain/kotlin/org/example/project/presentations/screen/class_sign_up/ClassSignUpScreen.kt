package org.example.project.presentations.screen.class_sign_up

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.presentations.components.StatusBarStyle
import org.example.project.presentations.dialog.FailureDialog
import org.example.project.presentations.dialog.SuccessDialog
import org.example.project.presentations.screen.class_sign_up.components.ClassSignUpContent
import org.example.project.presentations.utils.CollectWithLifecycle

@Composable
fun ClassSignUpScreen(
    viewModel: ClassSignUpViewModel,
    onBack: () -> Unit,
    onOpenSignedUpClass: () -> Unit
) {
    StatusBarStyle(darkIcons = true)

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var successMessage by remember { mutableStateOf<String?>(null) }
    var failureMessage by remember { mutableStateOf<String?>(null) }

    viewModel.event.CollectWithLifecycle { event ->
        when (event) {
            is ClassSignUpEvent.EnrollClassFailure -> {
                failureMessage = event.message
            }

            is ClassSignUpEvent.EnrollClassSuccess -> {
                successMessage =
                    "Bạn đã đăng ký lớp học ${event.courseClassName} thành công"
            }
        }
    }

    successMessage?.let { message ->
        SuccessDialog(
            title = "Thành công!",
            message = message,
            onDismiss = {
                successMessage = null
            }
        )
    }

    failureMessage?.let { message ->
        FailureDialog(
            title = "Thất bại",
            message = "Đăng ký môn thất bại, lý do: $message",
            onDismiss = {
                failureMessage = null
            }
        )
    }

    ClassSignUpContent(
        uiState = uiState,
        onBack = onBack,
        onSelectedSchedule = viewModel::openSelectedScheduleDialog,
        onDismissSelectedScheduleDialog = viewModel::dismissDialog,
        onEnrollClass = viewModel::enrollClass,
        onOpenSignedUpClass = onOpenSignedUpClass
    )
}