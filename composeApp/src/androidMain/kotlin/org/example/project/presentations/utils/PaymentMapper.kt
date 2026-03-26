package org.example.project.presentations.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.QrCode
import androidx.compose.ui.graphics.vector.ImageVector
import org.example.project.domain.model.PaymentType
import java.text.NumberFormat
import java.util.Locale

fun Long.toFormatAmount(): String {
    return NumberFormat.getNumberInstance(Locale("vi", "VN"))
        .format(this)
        .replace(",", ".")
}

fun Long.toFormatAmountAndD(): String {
    return NumberFormat.getNumberInstance(Locale("vi", "VN"))
        .format(this)
        .replace(",", ".") + "đ"
}

fun PaymentType.displayName(): String = when (this) {
    PaymentType.QR_BANK -> "Chuyển khoản ngân hàng"
    PaymentType.QR_BANK_DEMO -> "Chuyển khoản ngân hàng 1"
}

fun PaymentType.subtitle(): String = when (this) {
    PaymentType.QR_BANK -> "QRPAY"
    PaymentType.QR_BANK_DEMO -> "QRPAY 1"
}

fun PaymentType.icon(): ImageVector = when (this) {
    PaymentType.QR_BANK -> Icons.Outlined.QrCode
    PaymentType.QR_BANK_DEMO -> Icons.Outlined.AccountBalance
}