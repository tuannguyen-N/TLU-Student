package org.example.project.presentations.screen.tuition_payment.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.presentations.components.TabRowView
import org.example.project.presentations.components.TopScreenBar
import org.example.project.presentations.theme.ExtendedColors
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun TuitionPaymentContent(
    color: ExtendedColors = LocalExtendedColors.current,
    onBack: () -> Unit
) {
    val tabs = listOf("Thanh Toán" to null, "Lịch sử" to null)
    var selectedTab by remember { mutableIntStateOf(1) }

    Scaffold(
        containerColor = color.background,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            TopScreenBar<String>(
                title = "Thanh toán học phí",
                enableListItem = true,
                onBack = onBack,
                backgroundColor = color.white,
                contentColor = color.blackBackground
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 25.dp)
                .padding(top = 18.dp)
        ) {

            TabRowView(
                tabs = tabs,
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )

            Spacer(Modifier.height(20.dp))

            if (selectedTab == 0) {
                PaymentContent(color)
            } else {
                PaymentHistoryContent(color)
            }
        }
    }
}

@Preview
@Composable
fun PreviewPM() {
    TuitionPaymentContent(
        onBack = {}
    )
}