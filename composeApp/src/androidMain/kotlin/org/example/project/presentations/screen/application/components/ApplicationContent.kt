package org.example.project.presentations.screen.application.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.R
import org.example.project.data.remote.dto.application.ApplicationType
import org.example.project.presentations.components.ButtonView
import org.example.project.presentations.screen.application.ApplicationState
import org.example.project.presentations.screen.feedback.components.FeedbackLabel
import org.example.project.presentations.screen.feedback.components.ImportantNoteFeedbackCard
import org.example.project.presentations.theme.LocalExtendedColors
import org.example.project.presentations.utils.clearFocusOnTap

@Composable
fun ApplicationContent(
    modifier: Modifier = Modifier,
    uiState: ApplicationState,
    onApplicationChange: (ApplicationType) -> Unit,
    onSubjectExpandedChange: (Boolean) -> Unit,
    onAddFile: (Uri) -> Unit,
    onRemoveFile: () -> Unit,
    onSubmit: () -> Unit,
    onContentChange: (String) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { onAddFile(it) }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
            .clearFocusOnTap(),
    ) {
        ApplicationLabel(text = "Chủ đề", true)
        Box {
            OutlinedTextField(
                value = uiState.selectedApplicationType?.name ?: "",
                onValueChange = {},
                readOnly = true,
                placeholder = {
                    Text(
                        text = "Chọn chủ đề phản hồi",
                        color = LocalExtendedColors.current.gray,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.icon_down),
                        contentDescription = null,
                        tint = LocalExtendedColors.current.gray
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = LocalExtendedColors.current.gray,
                    unfocusedBorderColor = LocalExtendedColors.current.gray.copy(alpha = 0.2f)
                )
            )

            Surface(
                onClick = { onSubjectExpandedChange(true) },
                color = Color.Transparent,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .matchParentSize()
            ) {}

            if (uiState.subjectExpanded) {
                ApplicationTypeMenu(
                    applicationTypes = uiState.applicationTypes,
                    onSelected = onApplicationChange,
                    onDismiss = { onSubjectExpandedChange(false) }
                )
            }
        }

        FeedbackLabel(text = "Nội dung đơn từ", false)
        OutlinedTextField(
            value = uiState.content ?: "",
            onValueChange = onContentChange,
            placeholder = {
                Text(
                    text = "Nhập nội dung đơn từ của bạn nếu có....",
                    color = LocalExtendedColors.current.gray,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = LocalExtendedColors.current.gray,
                unfocusedBorderColor = LocalExtendedColors.current.gray.copy(alpha = 0.2f)
            ),
            maxLines = 5
        )

        PdfAttachmentContent(
            uiState = uiState,
            onRemoveFile = onRemoveFile,
            onOpenFilePicker = { launcher.launch("application/pdf") },
        )

        Spacer(modifier = Modifier.height(80.dp))

        ImportantNoteFeedbackCard()

        ButtonView(
            text = "Gửi phản hồi",
            backgroundColorRes = LocalExtendedColors.current.mainRed,
            textColorRes = Color.White,
            enabled = uiState.isFormValid,
            onClick = onSubmit,
            modifier = Modifier
                .padding(top = 30.dp, start = 50.dp, end = 50.dp, bottom = 30.dp)
                .height(50.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ApplicationLabel(text: String, isNeedAsterisk: Boolean = false) {
    Text(
        text = buildAnnotatedString {
            append(text)
            append(" ")
            withStyle(style = SpanStyle(color = LocalExtendedColors.current.red)) {
                if (isNeedAsterisk) append("*")
            }
        },
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 15.dp, bottom = 10.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun ApplicationContentPreview() {
    ApplicationContent(
        uiState = ApplicationState(
            applicationTypes = emptyList(),
            attachedFile = null,
            subjectExpanded = false
        ),
        onApplicationChange = {},
        onSubjectExpandedChange = {},
        onRemoveFile = {},
        onSubmit = {},
        onAddFile = {},
        onContentChange = {}
    )
}