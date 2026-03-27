//
//  ButtonView.swift
//  iosApp
//
//  Created by Tuan Nguyen on 3/27/26.
//
import SwiftUI

struct ButtonView: View {
    var text: String = "Lưu"
    var textSize: CGFloat = 16
    var textColor: Color = .white
    var backgroundColor: Color = .black
    var iconRes: String? = nil
    var endIconRes: Image? = nil
    var shape: CGFloat = 12
    var enabled: Bool = false
    var onClick: () -> Void = {}

    var body: some View {
        Button(action: {
            if enabled {
                onClick()
            }
        }) {
            HStack(spacing: 10) {
                
                if let icon = iconRes {
                    Image(icon)
                        .resizable()
                        .frame(width: 24, height: 24)
                        .foregroundColor(textColor)
                }
                
                Text(text)
                    .font(.system(size: textSize, weight: .medium))
                    .foregroundColor(textColor)
                
                if let endIcon = endIconRes {
                    endIcon
                        .resizable()
                        .frame(width: 24, height: 24)
                        .foregroundColor(textColor)
                }
            }
            .frame(maxWidth: .infinity)
            .frame(height: 50)
            .background(enabled ? backgroundColor : Color(hex: 0xE0E0E0))
            .cornerRadius(shape)
        }
        .disabled(!enabled)
    }
}
