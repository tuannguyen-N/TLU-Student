package org.example.project.presentations.screen.feedback.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import org.example.project.R
import org.example.project.domain.model.FeedBackState
import org.example.project.domain.model.SubjectOption
import org.example.project.presentations.components.ButtonView
import org.example.project.presentations.theme.LocalExtendedColors
import org.example.project.presentations.utils.clearFocusOnTap

@Composable
fun FeedbackFormContent(
    modifier: Modifier = Modifier,
    uiState: FeedBackState,
    onTitleChange: (String) -> Unit,
    onSubjectChange: (SubjectOption) -> Unit,
    onContentChange: (String) -> Unit,
    onSubjectExpandedChange: (Boolean) -> Unit,
    onAddImage: (Uri) -> Unit,
    onRemoveImage: (Uri) -> Unit,
    onSubmit: () -> Unit,
) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { onAddImage(it) }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clearFocusOnTap(),
    ) {
        FeedbackLabel(text = "Tiêu đề")
        OutlinedTextField(
            value = uiState.title,
            onValueChange = onTitleChange,
            placeholder = {
                Text(
                    text = "Nhập nội dung phản hồi của bạn....",
                    color = LocalExtendedColors.current.gray,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = LocalExtendedColors.current.gray,
                unfocusedBorderColor = LocalExtendedColors.current.gray.copy(alpha = 0.2f)
            ),
            singleLine = true,
        )

        FeedbackLabel(text = "Chủ đề", true)
        Box {
            OutlinedTextField(
                value = uiState.subject?.value ?: "",
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
                TypeFeedbackMenu(
                    onSelected = onSubjectChange,
                    onDismiss = { onSubjectExpandedChange(false) }
                )
            }
        }

        FeedbackLabel(text = "Nội dung phản hồi", true)
        OutlinedTextField(
            value = uiState.content,
            onValueChange = onContentChange,
            placeholder = {
                Text(
                    text = "Nhập nội dung phản hồi của bạn....",
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

        ImageAttachmentContent(
            uiState = uiState,
            onRemoveImage = onRemoveImage,
            onOpenImagePicker = { launcher.launch("image/*") },
        )

        Spacer(modifier = Modifier.height(8.dp))

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
fun FeedbackLabel(text: String, isNeedAsterisk: Boolean = false) {
    Text(
        text = buildAnnotatedString {
            append(text)
            append(" ")

            withStyle(
                style = SpanStyle(color = LocalExtendedColors.current.red)
            ) {
                if (isNeedAsterisk) append("*")
            }
        },
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 15.dp, bottom = 10.dp)
    )
}