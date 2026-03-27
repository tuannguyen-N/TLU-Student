import Foundation
import SwiftUI

extension Color {
    init(hex: UInt, alpha: Double = 1.0) {
            self.init(
                .displayP3,
                red: Double((hex >> 16) & 0xFF) / 255.0,
                green: Double((hex >> 8) & 0xFF) / 255.0,
                blue: Double(hex & 0xFF) / 255.0,
                opacity: alpha
            )
        }

    static let background = Color(hex: 0xF5F5F7)
    static let primary = Color(hex: 0x0066FF)
    static let onPrimary = Color(hex: 0xFFFFFF)

    static let mainBlue = Color(hex: 0x050C56)
    static let mainRed = Color(hex: 0x8D0000)
    static let red = Color(hex: 0xF32409)
    static let redLight = Color(hex: 0xFFD9D4)

    static let textPrimary = Color(hex: 0x111111)

    static let white = Color(hex: 0xFFFFFF)

    static let yellow = Color(hex: 0xFFA400)
    static let yellowLight = Color(hex: 0xFFF0D6)

    static let green = Color(hex: 0x16A634)
    static let greenLight = Color(hex: 0xDAFFE2)

    static let gray = Color(hex: 0x848484)
    static let lightGray = Color(hex: 0xD9D9D9)
    static let grayNavy = Color(hex: 0x64748B)

    static let fontBlue = Color(hex: 0x016DB7)

    static let purple = Color(hex: 0x7C3AED)
    static let orange = Color(hex: 0xF97416)
    static let seaSerpent = Color(hex: 0x3AC2D8)

    static let cardBackground = Color(hex: 0xF1F4F5)
    static let blackBackground = Color(hex: 0x0C0F10)

    static let yellowRanking = Color(hex: 0xEAB308)
    static let average = Color(hex: 0xF97316)
}
