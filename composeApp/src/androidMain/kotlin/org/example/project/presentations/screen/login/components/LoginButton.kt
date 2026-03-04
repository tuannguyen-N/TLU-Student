package org.example.project.presentations.screen.login.components

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.R
import org.example.project.presentations.components.ButtonView
import org.example.project.presentations.theme.LocalExtendedColors
import org.example.project.presentations.utils.MsalHelper

@Composable
fun LoginButton(
    onLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    ButtonView(
        iconRes = R.drawable.icon_microsoft,
        enabled = true,
        backgroundColorRes = LocalExtendedColors.current.mainRed,
        text = "Đăng nhập bằng microsoft",
        modifier = Modifier.padding(horizontal = 40.dp),
        textSize = 16.sp,
        onClick = onLogin
    )
}