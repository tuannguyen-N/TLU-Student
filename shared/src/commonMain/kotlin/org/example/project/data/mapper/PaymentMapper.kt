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
    PaymentType.VN_PAY -> "Chuyển khoản qua VNPAY"
    PaymentType.ZALO_PAY -> "Chuyển khoản qua ZaloPay"
}

fun PaymentType.subtitle(): String = when (this) {
    PaymentType.VN_PAY -> "VNPAY"
    PaymentType.ZALO_PAY -> "ZaloPay"
}