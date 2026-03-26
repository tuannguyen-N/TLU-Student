package org.example.project.presentations.screen.tuition_payment.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.presentations.components.ButtonView
import org.example.project.presentations.components.TopScreenBar
import org.example.project.presentations.theme.ExtendedColors
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun TuitionPaymentContent(
    color: ExtendedColors = LocalExtendedColors.current,
    onBack: () -> Unit
) {
    val verticalScroll = rememberScrollState()

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
                .verticalScroll(verticalScroll),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            TuitionCard()

            DetailTuitionCourse(color = color)

            PaymentMethodList(

            )

            ButtonView(
                text = "Thanh toán ngay",
                enabled = true,
                textColorRes = color.white,
                backgroundColorRes = color.red,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.padding(horizontal = 20.dp),
                endIconRes = Icons.AutoMirrored.Filled.ArrowForward
            )

            Spacer(Modifier.height(20.dp))
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