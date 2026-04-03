package org.example.project.data.mapper

import org.example.project.domain.model.PaymentType

fun Long.toFormatAmount(): String {
    val reversed = this.toString().reversed()
    val grouped = reversed.chunked(3).joinToString(".")
    return grouped.reversed()
}

fun Long.toFormatAmountAndD(): String {
    return "${this.toFormatAmount()}đ"
}

fun PaymentType.displayName(): String = when (this) {
    PaymentType.QR_BANK -> "Chuyển khoản ngân hàng"
    PaymentType.QR_BANK_DEMO -> "Chuyển khoản ngân hàng 1"
}

fun PaymentType.subtitle(): String = when (this) {
    PaymentType.QR_BANK -> "QRPAY"
    PaymentType.QR_BANK_DEMO -> "QRPAY 1"
}