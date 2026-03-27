//
//  AuthenticationErrorBottomSheet.swift
//  iosApp
//
//  Created by Tuan Nguyen on 3/27/26.
//

import SwiftUI

struct AuthenticationErrorBottomSheet: View {
    var onRetry: () -> Void = {}

    var body: some View {
        VStack(alignment: .center, spacing: 0) {
            
            Spacer().frame(height: 20)
            
            VStack(spacing: 0) {
                
                ZStack {
                    Circle()
                        .fill(Color(red: 1.0, green: 0.84, blue: 0.84))
                        .frame(width: 56, height: 56)
                    
                    Image(systemName: "exclamationmark.triangle.fill")
                        .resizable()
                        .scaledToFit()
                        .frame(width: 28, height: 28)
                        .foregroundColor(.red)
                }
                
                Spacer().frame(height: 16)
                
                Text("Authentication Error !")
                    .font(.system(size: 16, weight: .bold))
                    .foregroundColor(.red) // mainRed
                
                Spacer().frame(height: 10)
                
                Text("We couldn't find an account matching those credentials. Please try again !")
                    .font(.system(size: 14))
                    .foregroundColor(.red)
                    .multilineTextAlignment(.center)
            }
            .frame(maxWidth: .infinity)
            .padding(20)
            .background(Color(red: 0.98, green: 0.91, blue: 0.91))
            .cornerRadius(24)
            
            Spacer().frame(height: 24)
            
            ButtonView(
                text: "Retry",
                textSize: 16,
                textColor: .white,
                backgroundColor: .red,
                enabled: true,
                onClick: onRetry
            )
        }
        .padding(.horizontal, 36)
        .padding(.top, 16)
        .frame(maxWidth: .infinity)
        .background(Color.white)
    }
}
