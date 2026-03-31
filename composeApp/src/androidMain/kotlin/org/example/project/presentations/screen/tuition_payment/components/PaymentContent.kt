package org.example.project.presentations.screen.tuition_payment.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.presentations.components.ButtonView
import org.example.project.presentations.theme.ExtendedColors


@Composable
fun PaymentContent(color: ExtendedColors) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        item { TuitionCard() }

        item { DetailTuitionCourse(color = color) }

        item { PaymentMethodList() }

        item {
            ButtonView(
                text = "Thanh toán ngay",
                enabled = true,
                textColorRes = color.white,
                backgroundColorRes = color.red,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.padding(horizontal = 20.dp),
                endIconRes = Icons.AutoMirrored.Filled.ArrowForward
            )
        }

        item {
            Spacer(Modifier.height(20.dp))
        }
    }
}