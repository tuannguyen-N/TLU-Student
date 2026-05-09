package org.example.project.presentations.screen.class_signed_up

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.presentations.components.StatusBarStyle
import org.example.project.presentations.dialog.FailureDialog
import org.example.project.presentations.dialog.SuccessDialog
import org.example.project.presentations.screen.class_signed_up.components.CancelClassDialog
import org.example.project.presentations.screen.class_signed_up.components.SignedUpClassesContent
import org.example.project.presentations.theme.LocalExtendedColors
import org.example.project.presentations.utils.CollectWithLifecycle

@Composable
fun SignedUpClassesScreen(
    viewModel: SignedUpClassesViewModel,
    onBack: () -> Unit = {},
    onOpenTempSchedule: () -> Unit,
    onBackToHome: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var successMessage by remember { mutableStateOf<String?>(null) }
    var failureMessage by remember { mutableStateOf<String?>(null) }
    var pendingDeleteClass by remember {
        mutableStateOf<Pair<String, String>?>(null)
    }

    viewModel.event.CollectWithLifecycle { event ->
        when (event) {
            is SignedUpEvent.CancelClassFailure -> failureMessage = event.message
            is SignedUpEvent.CancelClassSuccess -> successMessage =
                "Bạn đã hủy lớp ${event.className} thành công"
        }
    }

    successMessage?.let { message ->
        SuccessDialog(
            title = "Thành công!",
            message = message,
            onDismiss = { successMessage = null }
        )
    }

    failureMessage?.let { message ->
        FailureDialog(
            title = "Thất bại",
            message = "Hủy lớp thất bại, lý do: $message",
            onDismiss = { failureMessage = null }
        )
    }

    pendingDeleteClass?.let { (subjectCode, classCode) ->
        CancelClassDialog(
            className = classCode,
            onConfirm = {
                viewModel.onDeleteClass(subjectCode, classCode)
                pendingDeleteClass = null
            },
            onDismiss = {
                pendingDeleteClass = null
            }
        )
    }

    StatusBarStyle(darkIcons = true)

    SignedUpClassesContent(
        uiState = uiState,
        color = LocalExtendedColors.current,
        onConfirm = onBackToHome,
        onBack = onBack,
        onDeleteClass = { subjectCode, classCode ->
            pendingDeleteClass = subjectCode to classCode
        },
        onOpenTempSchedule = onOpenTempSchedule
    )
}