package org.example.project.presentations.screen.login

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import org.example.project.presentations.screen.login.components.AuthenticationErrorBottomSheet
import org.example.project.presentations.screen.login.components.CenterContent
import org.example.project.presentations.screen.login.components.LoginButton
import org.example.project.presentations.utils.CollectWithLifecycle
import org.example.project.presentations.utils.MsalHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    onNavigateToHome: () -> Unit = {},
) {
    val context = LocalContext.current
    val activity = context as? Activity ?: return

    var showErrorSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    loginViewModel.event.CollectWithLifecycle { event ->
        when (event) {
            LoginUiEvent.OnNavigateToHome -> onNavigateToHome()
            LoginUiEvent.ShowError -> showErrorSheet = true
        }
    }

    if (showErrorSheet) {
        ModalBottomSheet(
            dragHandle = null,
            onDismissRequest = {
                showErrorSheet = false
            },
            sheetState = sheetState
        ) {
            AuthenticationErrorBottomSheet(
                onRetry = {
                    showErrorSheet = false
                },
            )
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
                onLogin = { loginViewModel.onLoginClick(activity)}
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
                        MsalHelper.signOut {  }
                        // TODO:
                    }
            )
        }
    }
}