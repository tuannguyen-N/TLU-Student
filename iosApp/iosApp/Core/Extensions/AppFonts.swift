//
//  AppFonts.swift
//  iosApp
//
//  Created by Tuan Nguyen on 3/27/26.
//

import Foundation

import SwiftUI

extension Text {
    
    func appTextStyle(
        _ style: AppTextStyle,
        color: Color = .black
    ) -> some View {
        
        self
            .font(font(for: style))
            .foregroundColor(color)
    }
    
    private func font(for style: AppTextStyle) -> Font {
        switch style {
            
        case .displayLarge:
            return .system(size: 57, weight: .regular)
        case .displayMedium:
            return .system(size: 45, weight: .regular)
        case .displaySmall:
            return .system(size: 36, weight: .regular)
            
        case .headlineLarge:
            return .system(size: 32, weight: .regular)
        case .headlineMedium:
            return .system(size: 28, weight: .regular)
        case .headlineSmall:
            return .system(size: 24, weight: .regular)
            
        case .titleLarge:
            return .system(size: 22, weight: .medium)
        case .titleMedium:
            return .system(size: 16, weight: .medium)
        case .titleSmall:
            return .system(size: 14, weight: .medium)
            
        case .bodyLarge:
            return .system(size: 16, weight: .regular)
        case .bodyMedium:
            return .system(size: 14, weight: .regular)
        case .bodySmall:
            return .system(size: 12, weight: .regular)
            
        case .labelLarge:
            return .system(size: 14, weight: .medium)
        case .labelMedium:
            return .system(size: 12, weight: .medium)
        case .labelSmall:
            return .system(size: 11, weight: .medium)
        }
    }
}
