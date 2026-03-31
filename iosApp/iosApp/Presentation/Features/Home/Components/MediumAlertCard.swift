//
//  HighAlertCard.swift
//  iosApp
//
//  Created by Tuan Nguyen on 3/30/26.
//

import SwiftUI
import Shared

struct MediumAlertCard: View {
    var item: AlertUiModel
    var onClickAction: () -> Void = {}
    
    var body: some View {
        ZStack {
            
            VStack(alignment: .leading, spacing: 0) {
                
                HStack(spacing: 10) {
                    Text("Cảnh Báo")
                        .appTextStyle(.bodySmall, color: Color.yellowLight)
                        .fontWeight(.semibold)
                }
                .padding(.horizontal, 10)
                .padding(.vertical, 5)
                .background(Color.yellowLight.opacity(0.1))
                .clipShape(RoundedRectangle(cornerRadius: 5))
                .padding(.bottom, 6)
                
                Text(item.title)
                    .appTextStyle(.titleMedium, color: Color.mainBlue)
                    .fontWeight(.semibold)
                
                Text(item.content)
                    .appTextStyle(.bodySmall, color: Color.mainBlue)
                    .lineLimit(2)
                    .truncationMode(.tail)
                    .padding(.top, 8)
                    .padding(.bottom, 15)
                
                Text("Hành động ngay")
                    .appTextStyle(.bodyMedium, color: .white)
                    .fontWeight(.semibold)
                    .padding(.horizontal, 32)
                    .padding(.vertical, 8)
                    .background(Color.yellow)
                    .clipShape(RoundedRectangle(cornerRadius: 6))
                    .onTapGesture {
                        onClickAction()
                    }
                    .frame(maxWidth: .infinity, alignment: .center)
            }
            .padding(13)
            .frame(maxWidth: .infinity, alignment: .leading)
        }
        .frame(width: 220, height: 165)
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: 12))
        .shadow(radius: 2)
        .padding(.leading, 10)
        .padding(.vertical, 3)
    }
}
