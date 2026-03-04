package org.example.project.presentations.screen.login

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.presentations.screen.login.components.CenterContent
import org.example.project.presentations.screen.login.components.LoginButton
import org.example.project.presentations.utils.MsalHelper

@Preview
@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit = {},
    loginViewModel: LoginViewModel = viewModel()
) {
    val context = LocalContext.current
    val activity = context as Activity

    LaunchedEffect(Unit) {
        loginViewModel.event.collect { event ->
            when (event) {
                LoginUiEvent.OnNavigateToHome -> onNavigateToHome()
                LoginUiEvent.ShowError -> TODO()
            }
        }
    }

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        CenterContent(modifier = Modifier.align(Alignment.Center))

        Column(
            modifier = Modifier.align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginButton(
                onLogin = {
                    MsalHelper.signIn(activity) { use, token ->
                        if (token != null) {
                            loginViewModel.onSignMsalSuccess(token)
                        }
                    }
                }
            )

            HorizontalDivider(
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 10.dp)
                    .padding(horizontal = 40.dp)
            )

            Text(
                text = "Hướng dẫn sử dụng",
                style = MaterialTheme.typography.titleSmall,
                color = Color.Black,
                modifier = Modifier
                    .padding(bottom = 30.dp)
                    .clickable {
                        MsalHelper.signOut {
                            Log.e("LOGIN", "LoginScreen: $it")
                        }
                    }
            )
        }
    }
}