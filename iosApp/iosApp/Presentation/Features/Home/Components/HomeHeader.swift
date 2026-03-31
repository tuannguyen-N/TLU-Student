//
//  HomeHeader.swift
//  iosApp
//
//  Created by Tuan Nguyen on 3/30/26.
//

import SwiftUI

struct HomeHeader: View {
    let name: String
    let studentCode: String
    let onOpenProfile: () -> Void
    let onOpenNotification: () -> Void
    let isProfileReady: Bool

    var body: some View {
        HStack {
            HStack(alignment: .center, spacing: 12) {
                
                Button(action: onOpenProfile) {
                    if isProfileReady {
                        Image("ic_launcher_background")
                            .resizable()
                            .scaledToFill()
                            .frame(width: 40, height: 40)
                            .clipShape(Circle())
                            .overlay(
                                Circle().stroke(Color.white, lineWidth: 1)
                            )
                    } else {
                        Circle()
                            .fill(Color.gray.opacity(0.3))
                            .frame(width: 40, height: 40)
                            .redacted(reason: .placeholder)
                    }
                }
                .disabled(!isProfileReady)

                VStack(alignment: .leading, spacing: 2) {
                    Text(name)
                        .foregroundColor(.white)
                        .appTextStyle(.titleMedium)
                        .fontWeight(.semibold)

                    Text("MSV: \(studentCode)")
                        .foregroundColor(.white)
                        .appTextStyle(.bodyMedium)
                        .fontWeight(.medium)
                }
                .frame(maxWidth: .infinity, alignment: .leading)

                Button(action: onOpenNotification) {
                    Image(systemName: "bell")
                        .resizable()
                        .frame(width: 24, height: 24)
                        .foregroundStyle(Color.white)
                }
            }
            .padding(.horizontal, 24)
            .padding(.bottom, 12)
            .safeAreaInset(edge: .top) {
                Color.clear.frame(height: 0)
            }
        }
        .frame(maxWidth: .infinity)
        .background(Color.mainRed)
    }
}
