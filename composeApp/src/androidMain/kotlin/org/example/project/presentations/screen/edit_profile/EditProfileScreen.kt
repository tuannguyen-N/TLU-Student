package org.example.project.presentations.screen.edit_profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.project.R
import org.example.project.presentations.components.Base64Image
import org.example.project.presentations.components.ButtonView
import org.example.project.presentations.components.StatusBarStyle
import org.example.project.presentations.dialog.ExitConfirmDialog
import org.example.project.presentations.screen.edit_profile.components.EditTextView
import org.example.project.presentations.theme.LocalExtendedColors
import org.example.project.presentations.utils.CollectWithLifecycle

@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel,
    onBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val imageBase64 = uiState.avatarBase64
//    val launcher = rememberLauncherForActivityResult(
//        ActivityResultContracts.GetContent()
//    ) {uri ->
//        // TODO:
//    }

    viewModel.events.CollectWithLifecycle { event ->
        when (event) {
            EditProfileUIEvent.OnNavigateBack -> {
                onBack()
            }
        }
    }

    StatusBarStyle(darkIcons = true)

    LazyColumn(
        modifier = Modifier
            .statusBarsPadding()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    onClick = {
                        viewModel.onCancelEditProfile()
                    }, color = Color.Transparent, modifier = Modifier
                        .padding(start = 10.dp)
                        .clip(
                            RoundedCornerShape(15.dp)
                        )
                ) {
                    Text(
                        text = "Cancel",
                        style = MaterialTheme.typography.bodyLarge,
                        color = LocalExtendedColors.current.red,
                        modifier = Modifier.padding(10.dp)
                    )
                }

                Box(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    if (imageBase64 != null && imageBase64 != "") {
                        Base64Image(
                            base64String = imageBase64,
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                                .border(
                                    BorderStroke(1.dp, Color.White), CircleShape
                                )
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                                .background(LocalExtendedColors.current.gray)
                                .border(
                                    BorderStroke(1.dp, Color.White), CircleShape
                                )
                                .padding(20.dp)
                        )
                    }
//
//                    IconButton(
//                        onClick = {
//                            launcher.launch("image/*")
//                        }, modifier = Modifier
//                            .size(35.dp)
//                            .align(Alignment.BottomEnd)
//                    ) {
//                        Image(
//                            painter = painterResource(R.drawable.icon_change_avatar),
//                            contentDescription = null
//                        )
//                    }
                }
            }
        }

        item {
            EditTextView(
                R.drawable.icon_mail,
                "Email",
                uiState.email.orEmpty(),
                "xxx@gmail.com",
                uiState.emailError,
                {
                    viewModel.onEmailChange(it)
                })
            EditTextView(
                R.drawable.icon_phone_number,
                "Số điện thoại",
                uiState.phone.orEmpty(),
                "0987654321",
                uiState.phoneError,
                {
                    viewModel.onPhoneChange(it)
                })
            EditTextView(
                R.drawable.icon_adress,
                "Địa chỉ",
                uiState.address.orEmpty(),
                "Hồ Chí Minh",
                uiState.addressError,
                {
                    viewModel.onAddressChange(it)
                }
            )
            EditTextView(
                R.drawable.icon_name,
                "Họ tên người liên hệ",
                uiState.nameContact.orEmpty(),
                "Nguyễn Văn A",
                uiState.nameContactError,
                {
                    viewModel.onNameContactChange(it)
                })
            EditTextView(
                R.drawable.icon_phone_number,
                "Số điện thoại người liên hệ",
                uiState.phoneContact.orEmpty(),
                "0123123123123",
                uiState.phoneContactError,
                {
                    viewModel.onPhoneContactChange(it)
                })
            EditTextView(
                R.drawable.icon_adress,
                "Địa chỉ người liên hệ",
                uiState.addressContact.orEmpty(),
                "Hồ Chí Minh",
                uiState.addressContactError,
                {
                    viewModel.onAddressContactChange(it)
                })
        }

        item {
            ButtonView(
                onClick = {
                    // TODO:
                },
                backgroundColorRes = LocalExtendedColors.current.mainBlue,
                textColorRes = LocalExtendedColors.current.white,
                enabled = viewModel.isButtonEnabled,
                modifier = Modifier
                    .padding(top = 35.dp, bottom = 40.dp)
                    .width(220.dp)
            )
        }
    }

    if (uiState.isShowExitDialog) {
        ExitConfirmDialog(
            onDismiss = { viewModel.onDismissExitDialog() },
            onConfirm = {
                onBack()
            }
        )
    }
}