//
//  AppColors.swift
//  iosApp
//
//  Created by Tuan Nguyen on 3/20/26.
//

import Foundation
import SwiftUI

extension Color {
    init(hex: UInt, alpha: Double = 1.0) {
        self.init(
            .sRGB,
            red: Double((hex >> 16) & 0xFF) / 255.0,
            green: Double((hex >> 8) & 0xFF) / 255.0,
            blue: Double(hex & 0xFF) / 255.0,
            opacity: alpha
        )
    }

    static let appBackground = Color(hex: 0xF5F5F5)
    static let mainRed = Color(hex: 0xE53935)
    static let mainBlue = Color(hex: 0x1565C0)
    static let appRed = Color(hex: 0xE53935)
    static let redLight = Color(hex: 0xFFD9D4)
    static let textPrimary = Color(hex: 0x212121)
    static let primary = Color(hex: 0x1565C0)
    static let onPrimary = Color(hex: 0xFFFFFF)
    static let appWhite = Color(hex: 0xFFFFFF)
    static let yellow = Color(hex: 0xFFA400)
    static let yellowLight = Color(hex: 0xFFF0D6)
    static let green = Color(hex: 0x16A634)
    static let greenLight = Color(hex: 0xDAFFE2)
    static let gray = Color(hex: 0x848484)
    static let grayButton = Color(hex: 0xD9D9D9)
    static let fontBlue = Color(hex: 0x016DB7)
    static let yellowRanking = Color(hex: 0xEAB308)
    static let average = Color(hex: 0xF97316)
    static let purple = Color(hex: 0x7C3AED)
    static let orange = Color(hex: 0xF97416)
    static let seaSerpent = Color(hex: 0x3AC2D8)
    static let grayNavy = Color(hex: 0x64748B)
}
