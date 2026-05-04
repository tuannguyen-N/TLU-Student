package org.example.project.presentations.screen.tuition_payment.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.PaymentType
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun PaymentMethodList(
    modifier: Modifier = Modifier,
    selectedType: PaymentType? = PaymentType.VN_PAY,
    onSelect: (PaymentType) -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Chọn phương thức thanh toán",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(16.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PaymentType.entries.forEach { type ->
                PaymentMethodItem(
                    type = type,
                    isSelected = type == selectedType,
                    onClick = { onSelect(type) },
                    color = LocalExtendedColors.current
                )
            }
        }
    }
}