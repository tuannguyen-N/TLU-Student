package org.example.project.presentations.screen.login

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.R
import org.example.project.presentations.components.ButtonView
import org.example.project.presentations.theme.LocalExtendedColors
import org.example.project.presentations.utils.MsalHelper

@Preview
@Composable
fun LoginScreen() {
    val context = LocalContext.current
    val activity = context as Activity

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.tlu_logo),
                contentDescription = null,
                modifier = Modifier
                    .height(50.dp)
                    .padding(bottom = 10.dp)
            )

            Text(
                text = "CỔNG THÔNG TIN ĐÀO TẠO",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = LocalExtendedColors.current.mainBlue
            )
        }

        Column(
            modifier = Modifier.align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ButtonView(
                iconRes = R.drawable.icon_microsoft,
                enabled = true,
                backgroundColorRes = LocalExtendedColors.current.mainRed,
                text = "Đăng nhập bằng microsoft",
                modifier = Modifier.padding(horizontal = 40.dp),
                textSize = 16.sp,
                onClick = {
                    MsalHelper.signIn(activity) { token ->
                        if (token != null) {
                            val content = "Token: $token"
                        }
                    }
                }
            )

            HorizontalDivider(modifier = Modifier
                .padding(top = 20.dp, bottom = 10.dp)
                .padding(horizontal = 40.dp))

            Text(
                text = "Hướng dẫn sử dụng",
                style = MaterialTheme.typography.titleSmall,
                color = Color.Black,
                modifier = Modifier
                    .padding(bottom = 30.dp)
                    .clickable {
                        MsalHelper.signOut {
                            Log.e("LOGIN", "LoginScreen: $it",)
                        }
                    }
            )
        }
    }
}